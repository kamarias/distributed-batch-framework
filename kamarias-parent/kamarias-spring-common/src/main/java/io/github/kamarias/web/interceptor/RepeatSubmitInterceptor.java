package io.github.kamarias.web.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import io.github.kamarias.utils.http.ServletUtils;
import io.github.kamarias.utils.string.StringUtils;
import io.github.kamarias.web.annotation.RepeatSubmit;
import io.github.kamarias.web.filter.RepeatableFilter;
import io.github.kamarias.web.wrapper.RepeatedlyRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyuxing@gogpay.cn
 * @date 2023/2/1 15:21
 */
public class RepeatSubmitInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepeatSubmitInterceptor.class);

    private final String errResponse = "{\"msg\":\"不允许重复提交，请稍后再试\",\"code\":500}";


    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";


    @Value("${token.header:cppMv4C6cjZqZWQVBrws}")
    private String header;

    public final StringRedisTemplate redisTemplate;

    @Value("${spring.application.name:app}")
    private String APP_NAME;

    private final String REPEAT_SUBMIT = "repeat_submit:";


    /**
     * 间隔时间，单位:秒 默认10秒
     * <p>
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    private int intervalTime = 10;

    public RepeatSubmitInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit repeatSubmit = AnnotationUtils.getAnnotation(method, RepeatSubmit.class);
            if (repeatSubmit != null && this.isRepeatSubmit(request)) {
                ServletUtils.renderString(response, this.errResponse);
                LOGGER.error("不允许重复提交数据", new RuntimeException("duplicate submission is not allow"));
                return false;
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交
     *
     * @param request http请求Servlet
     * @return 是否重复提交
     */
    public boolean isRepeatSubmit(HttpServletRequest request) {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = repeatedlyRequest.getBodyString();
        }

        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(nowParams)) {
            nowParams = JSONObject.toJSONString(request.getParameterMap());
        }
        Map<String, String> nowDataMap = new HashMap<>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, String.valueOf(System.currentTimeMillis()));
        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();
        // 唯一值（没有消息头则使用请求地址）
        String submitKey = request.getHeader(header);
        if (StringUtils.isEmpty(submitKey)) {
            submitKey = url;
        }
        // 唯一标识（指定key + 消息头）
        String cacheRepeatKey = APP_NAME + ":" + REPEAT_SUBMIT + submitKey;
        ValueOperations<String, String> operation = redisTemplate.opsForValue();
        String sessionObj = operation.get(cacheRepeatKey);
        if (sessionObj != null) {
            Map<String, String> sessionMap = JSONObject.parseObject(sessionObj, new TypeReference<Map<String, String>>() {
            });
            if (sessionMap.containsKey(url)) {
                Map<String, String> preDataMap = JSONObject.parseObject(sessionMap.get(url), new TypeReference<Map<String, String>>() {
                });
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                    return true;
                }
            }
        }
        Map<String, String> cacheMap = new HashMap<>();
        cacheMap.put(url, JSONObject.toJSONString(nowDataMap));
        redisTemplate.opsForValue().set(cacheRepeatKey, JSONObject.toJSONString(cacheMap), intervalTime, TimeUnit.SECONDS);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, String> nowMap, Map<String, String> preMap) {
        String nowParams = nowMap.get(REPEAT_PARAMS);
        String preParams = preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, String> nowMap, Map<String, String> preMap) {
        long time1 = Long.parseLong(nowMap.get(REPEAT_TIME));
        long time2 = Long.parseLong(preMap.get(REPEAT_TIME));
        return (time1 - time2) < (this.intervalTime * 1000L);
    }
}

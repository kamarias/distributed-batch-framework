package io.github.kamarias.token;

import io.github.kamarias.bean.AuthLogin;
import io.github.kamarias.exception.TokenAnalyzeException;
import io.github.kamarias.properties.TokenProperties;
import io.github.kamarias.utils.http.ServletUtils;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * token工具类
 *
 * @author wangyuxing@gogpay.cn
 * @date 2023/2/21 14:53
 */
public class RedisAuthTokenService implements AuthTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAuthTokenService.class);

    /**
     * 登录key前缀
     */
    private final String LOGIN_KEY = "login_cache::";

    /**
     * 登录用户Id唯一值
     */
    private final String SINGLE_KEY = "single_key::";


    public final RedisTemplate<String, Object> redisTemplate;

    /**
     * token 配置
     */
    private final TokenProperties tokenProperties;

    /**
     * redis key 过期返回值
     */
    private final long EXPIRED_VALUE = -2;

    public RedisAuthTokenService(RedisTemplate<String, Object> redisTemplate, TokenProperties tokenProperties) {
        this.redisTemplate = redisTemplate;
        this.tokenProperties = tokenProperties;
    }

    /**
     * 创建token
     *
     * @param o   登录对象实体
     * @param <T> 继承LoginObject对象
     * @return 返回值
     */
    @Override
    public <T extends AuthLogin> String createToken(T o) {
        if (tokenProperties.isSinglePoint()) {
            Assert.notNull(o.getId(), "登录对象Id不能为空");
            return createSingleRedisToken(o);
        }
        return createRedisToken(o);
    }

    /**
     * 移除token
     *
     * @return 移除结果
     */
    @Override
    public boolean deleteToken() {
        if (tokenProperties.isSinglePoint()) {
            return removeSingleRedisToken();
        }
        return removeRedisToken();
    }


    /**
     * 移除token
     *
     * @param token 移除的令牌
     * @return 移除结果
     */
    @Override
    public boolean deleteToken(String token) {
        if (tokenProperties.isSinglePoint()) {
            return removeSingleRedisToken(token);
        }
        return removeRedisToken(token);
    }

    /**
     * 解析token
     *
     * @param <T> 继承 AuthLogin 的类
     * @return 解析结果
     */
    public <T extends AuthLogin> T analyzeToken() {
        if (tokenProperties.isSinglePoint()) {
            return analyzeSingleRedisToken();
        }
        return analyzeRedisToken();
    }

    /**
     * 解析token
     *
     * @param str 令牌
     * @param <T> 继承 AuthLogin 的类
     * @return 解析结果
     */
    public <T extends AuthLogin> T analyzeToken(String str) {
        if (tokenProperties.isSinglePoint()) {
            return analyzeSingleRedisToken(str);
        }
        return analyzeRedisToken(str);
    }


    /**
     * 创建单点token
     *
     * @param o   生成的对象
     * @param <T> 继承UuidObject 的类
     * @return 返回生成key
     */
    private <T extends AuthLogin> String createSingleRedisToken(T o) {
        String jwtPassword = generateJwtToken(o);
        // 存入缓存中
        this.redisTemplate.opsForValue().set(loginKeyGenerator(o.getUuid()), o, tokenProperties.getExpireTime(), tokenProperties.getUnit());
        // 设置登录绑定的uuid
        this.redisTemplate.opsForValue().set(singleKeyGenerator(o.getId()), o.getUuid(), tokenProperties.getExpireTime(), tokenProperties.getUnit());
        return jwtPassword;
    }



    private <T extends AuthLogin> String generateJwtToken(T o) {
        Assert.notNull(o.getRoles(), "登录对象角色不能为空");
        Assert.notNull(o.getPermissions(), "登录对象权限不能为空");
        // 生成 jwt 密钥
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtPassword = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setId(o.getUuid())
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecret())
                .compact();
        return jwtPassword;
    }

    /**
     * 删除token令牌
     *
     * @return 移除令牌结果
     */
    private boolean removeSingleRedisToken() {
        AuthLogin o = analyzeSingleRedisToken();
        return Boolean.TRUE.equals(redisTemplate.delete(loginKeyGenerator(o.getUuid())))
                && Boolean.TRUE.equals(redisTemplate.delete(singleKeyGenerator(o.getId())));
    }

    /**
     * 删除token令牌
     *
     * @param str 令牌
     * @return 移除令牌结果
     */
    private boolean removeSingleRedisToken(String str) {
        AuthLogin o = analyzeSingleRedisToken(str);
        return Boolean.TRUE.equals(redisTemplate.delete(loginKeyGenerator(o.getUuid())))
                && Boolean.TRUE.equals(redisTemplate.delete(singleKeyGenerator(o.getId())));
    }

    /**
     * 解析 redis
     *
     * @param <T> 继承UuidObject的解析对象
     * @return 解析成功的对象
     */
    private <T extends AuthLogin> T analyzeSingleRedisToken() {
        T token = analyzeRedisToken();
        String uuid = String.valueOf(redisTemplate.opsForValue().get(singleKeyGenerator(token.getId())));
        if (token.getUuid().equals(uuid)) {
            return token;
        }
        LOGGER.warn("当前登录账号已在其它地方登录");
        // 移除之前登录的缓存key
        redisTemplate.delete(loginKeyGenerator(token.getUuid()));
        throw new TokenAnalyzeException("当前登录账号已在其它地方登录");
    }

    /**
     * 解析 redis
     *
     * @param str 密钥
     * @param <T> 继承UuidObject的解析对象
     * @return 解析成功的对象
     */
    private <T extends AuthLogin> T analyzeSingleRedisToken(String str) {
        T token = analyzeRedisToken(str);
        String uuid = String.valueOf(redisTemplate.opsForValue().get(singleKeyGenerator(token.getId())));
        if (token.getUuid().equals(uuid)) {
            return token;
        }
        LOGGER.warn("当前登录账号已在其它地方登录");
        // 移除之前登录的缓存key
        redisTemplate.delete(loginKeyGenerator(token.getUuid()));
        throw new TokenAnalyzeException("当前登录账号已在其它地方登录");
    }

    /**
     * 创建 token
     *
     * @param o   生成的对象
     * @param <T> 继承UuidObject 的类
     * @return 返回生成key
     */
    private <T extends AuthLogin> String createRedisToken(T o) {
        String jwtPassword = generateJwtToken(o);
        // 存入缓存中
        this.redisTemplate.opsForValue().set(loginKeyGenerator(o.getUuid()), o, tokenProperties.getExpireTime(), tokenProperties.getUnit());
        return jwtPassword;
    }

    /**
     * 删除token令牌
     *
     * @return 移除令牌结果
     */
    private boolean removeRedisToken() {
        AuthLogin o = analyzeRedisToken();
        return Boolean.TRUE.equals(redisTemplate.delete(loginKeyGenerator(o.getUuid())));
    }

    /**
     * 删除token令牌
     *
     * @param str 令牌
     * @return 移除令牌结果
     */
    private boolean removeRedisToken(String str) {
        AuthLogin o = analyzeRedisToken(str);
        return Boolean.TRUE.equals(redisTemplate.delete(loginKeyGenerator(o.getUuid())));
    }

    /**
     * 解析 redis
     *
     * @param <T> 继承UuidObject的解析对象
     * @return 解析成功的对象
     */
    private <T extends AuthLogin> T analyzeRedisToken() {
        HttpServletRequest request = ServletUtils.getRequest();
        String header = request.getHeader(tokenProperties.getAuthHeader());
        if (StringUtils.isEmpty(header)) {
            LOGGER.info("登录令牌已过期：授权请求头为空");
            throw new TokenAnalyzeException("登录令牌已过期");
        }
        return analyzeRedisToken(header);
    }

    /**
     * 解析 redis
     *
     * @param str 密钥
     * @param <T> 继承UuidObject的解析对象
     * @return 解析成功的对象
     */
    private <T extends AuthLogin> T analyzeRedisToken(String str) {
        String redisUuid;
        try {
            redisUuid = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(removePrefix(str))
                    .getBody().getId();
        } catch (Exception e) {
            // 移除redis 的缓存
            LOGGER.info("登录令牌已过期：登录令牌解析错误");
            throw new TokenAnalyzeException("登录令牌错误或已失效");
        }
        redisUuid = loginKeyGenerator(redisUuid);
        long expireTime = redisTemplate.opsForValue().getOperations().getExpire(redisUuid);
        if (expireTime == EXPIRED_VALUE) {
            LOGGER.info("登录令牌已过期：令牌过期");
            throw new TokenAnalyzeException("登录令牌已过期");
        }
        T t = (T) redisTemplate.opsForValue().get(redisUuid);
        if (Objects.isNull(t)) {
            LOGGER.info("登录令牌已过期：令牌过期");
            throw new TokenAnalyzeException("登录令牌已过期");
        }
        if (tokenProperties.getRefreshDate() >= expireTime) {
            // 续期token
            renewalRedisToken(redisUuid, t);
        }
        return t;
    }

    @Override
    public <T extends AuthLogin> void renewalToken(String token) {
        String redisUuid;
        try {
            redisUuid = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecret())
                    .parseClaimsJws(removePrefix(token))
                    .getBody().getId();
        } catch (Exception e) {
            // 移除redis 的缓存
            LOGGER.info("登录令牌已过期：登录令牌解析错误");
            throw new TokenAnalyzeException("登录令牌错误或已失效");
        }
        redisUuid = loginKeyGenerator(redisUuid);
        T t = (T) redisTemplate.opsForValue().get(redisUuid);
        renewalRedisToken(redisUuid, t);
    }

    private <T extends AuthLogin> void renewalRedisToken(String key, T t) {
        // 续期token
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(key, t, tokenProperties.getExpireTime(), tokenProperties.getUnit());
    }

    /**
     * 校验请求前缀并移除 token 请求前缀
     *
     * @param token 带前缀的 token
     * @return 返回不带前缀的 token
     */
    private String removePrefix(String token) {
        if (token.contains(tokenProperties.getAuthHeaderPrefix())) {
            return token.replace(tokenProperties.getAuthHeaderPrefix(), "");
        }
        throw new IllegalArgumentException("请求前缀异常");
    }

    /**
     * 生成登录key
     *
     * @param uuid 登录uuid
     * @return 返回结果
     */
    private String loginKeyGenerator(String uuid) {
        return this.LOGIN_KEY + uuid;
    }

    /**
     * 生成单一key
     *
     * @param singleKey 单一key
     * @return 返回结果
     */
    private String singleKeyGenerator(String singleKey) {
        return this.LOGIN_KEY + SINGLE_KEY + singleKey;
    }

}

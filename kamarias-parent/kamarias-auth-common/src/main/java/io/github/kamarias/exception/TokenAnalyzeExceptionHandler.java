package io.github.kamarias.exception;

import io.github.kamarias.dto.AjaxResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用最高优先级处理
 * hibernate-validator 参数校验异常处理
 *
 * @author wangyuxing@gogpay.cn
 * @date @DATE @TIME
 */
@ConditionalOnMissingBean(TokenAnalyzeExceptionHandler.class)
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TokenAnalyzeExceptionHandler {


    /**
     * 自定义类参数异常处理
     */
    @ExceptionHandler(TokenAnalyzeException.class)
    public AjaxResult<Object> handleTokenAnalyzeExceptionHandler(TokenAnalyzeException e) {
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

}

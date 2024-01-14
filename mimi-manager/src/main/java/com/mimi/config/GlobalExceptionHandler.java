package com.mimi.config;

import com.mimi.core.common.R;
import com.mimi.core.common.exception.MimiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 * 全局异常处理配置类
 */
@Slf4j
@RestControllerAdvice("com.mimi")
public class GlobalExceptionHandler {

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return ResponseResult
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("", e);
        return R.error("请求参数 " + e.getParameterName() + " 不能为空");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MimiException.class)
    public R parameterMimiExceptionHandler(MimiException e) {
        log.error("", e);
        return R.error(e.getMessage());
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseInfo
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error("", e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return R.error( fieldError.getDefaultMessage());
//                return new ResponseResult(ResultEnum.PARAMETER_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return R.error("参数异常");
    }
}

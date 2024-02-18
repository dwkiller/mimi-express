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

    @ExceptionHandler(value = Throwable.class)
    public R<?> exceptionHandler(Throwable throwable){
        throwable.printStackTrace();
        String message = throwable.getMessage();
        Throwable innerThrowable = throwable.getCause();
        if(innerThrowable!=null){
            message = innerThrowable.getCause()!=null?innerThrowable.getCause().getMessage():innerThrowable.getMessage();
        }
        return R.error(message);
    }

}

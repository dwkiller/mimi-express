package com.mimi.config;

import com.mimi.core.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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

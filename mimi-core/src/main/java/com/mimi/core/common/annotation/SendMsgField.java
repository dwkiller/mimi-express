package com.mimi.core.common.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface SendMsgField {
    String value() default "";

    String text() default "";

    String translateNameBean() default "";
}

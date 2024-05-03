package com.example.management.common.cache;

import java.lang.annotation.*;

/**
 * @author 14158
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 60 * 1000;

    String name() default "";

}
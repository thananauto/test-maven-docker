package com.qa.annotation;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FrameworkAnnotation {

    String[] suite() default "";
    String[] category() default "";
    String code();
}

package com.onyx.commons.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/* Metadata Tag for TODO items*/
@SuppressWarnings("java:S1135")
@Repeatable(TODOContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, METHOD})
public @interface TODO {

    String author() default "";

    String date() default "";

    /**
     * Alias for (@link #value)
     *
     * @return
     */
    String task() default "";

    String value();

    //TODO - task() and value() should point to the same thing
}

//https://medium.com/@afinlay/how-much-do-you-actually-know-about-annotations-in-java-b999e100b929
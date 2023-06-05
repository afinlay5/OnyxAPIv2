package com.onyx.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* Metadata Tag for TODO items*/
@SuppressWarnings("java:S1135")
@Repeatable(TODOContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TODO {

    String author();

    String date();

    String task();

    String value();
}

//https://medium.com/@afinlay/how-much-do-you-actually-know-about-annotations-in-java-b999e100b929
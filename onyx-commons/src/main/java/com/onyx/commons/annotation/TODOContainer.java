package com.onyx.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* Container for Repeatable {@link TODO} Annotation*/
@SuppressWarnings("java:S1135")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface TODOContainer {
    TODO[] value();
}

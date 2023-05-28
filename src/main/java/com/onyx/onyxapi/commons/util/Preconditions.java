package com.onyx.onyxapi.commons.util;

import org.apache.commons.lang3.StringUtils;

public final class Preconditions {


    /**
     * Precondition check against not null blank or whitespace
     *
     * @param val input value
     * @throws {@link IllegalArgumentException} if null, blank, or whitepsace
     * @return input value
     */
    public static String requireNotBlank(String val, String msg) {
        if(StringUtils.isBlank(val))
            throw new IllegalArgumentException(msg);
        else return val;
    }

    private Preconditions () {}
}

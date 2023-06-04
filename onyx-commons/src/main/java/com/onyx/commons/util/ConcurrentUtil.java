package com.onyx.commons.util;

public class ConcurrentUtil {
    public static synchronized void resetInterruptedFlag() {
        if (Thread.currentThread().isInterrupted())
            Thread.currentThread().interrupt();
    }

    private ConcurrentUtil() {}
}

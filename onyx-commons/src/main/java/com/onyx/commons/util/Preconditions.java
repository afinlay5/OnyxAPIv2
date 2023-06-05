package com.onyx.commons.util;

import com.google.common.collect.Range;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/* Validates input for legal arguments */
public final class Preconditions {

    private static final String EXC_STR_MISSING_OR_EMPTY = "excStr is required and is missing or blank";
    private static final String EXC_SUBSTR_MISSING_OR_EMPTY = "excSubStr is required and is missing or blank";
    private static final String EXC_STR_MUST_BE_POSITIVE = "%s must be positive";
    private static final String EXC_STR_MUST_NOT_BE_NEGATIVE = "%s must be negative";
    private static final String EXC_STR_WITHIN_RANGE = " %s must be within range %d-%d";
    private static final String IS_REQD_AND_MISSING = " %s is required and missing";


    /*
       Validations prefixed with check represent checks against Illegal Args supplied in an Application
     */

    /**
     * Validate that Map is not null, empty,or contains a null value
     *
     * @param map    {@link Map} to validate
     * @param excStr Exception String supplied to IAE
     * @param <K>    key Type
     * @param <V>    value Type
     * @param <R>    Map<Key, Value>
     * @return {@code map} if passed validation successfully
     * @throws IllegalArgumentException if {@code map} is null, empty, or contains a null value
     */
    public static <K, V, R extends Map<K, V>> R checkMapEntriesNotEmpty(R map, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }

        if (map.containsValue(null)) {
            throw new IllegalArgumentException(excStr);
        }

        return map;
    }

    /**
     * Validate that Map is not null and contains keys
     *
     * @param map    {@link Map} to validate
     * @param excStr Exception String supplied to IAE
     * @param keys   keys to check for
     * @param <K>    key Type
     * @param <V>    value Type
     * @return {@code map} if passed validation successfully
     * @throws IllegalArgumentException if {@code map} is null or does not have keys
     */
    @SafeVarargs
    public static <K, V> Map<K, V> checkMapHasKeys(Map<K, V> map, String excStr, K... keys) {
        val set = Stream.of(checkArrayNotEmpty(keys, excStr))
                .collect(Collectors.toUnmodifiableSet());
        return checkMapHasKeys(map, set, excStr);
    }

    /**
     * Validate that Map is not null or empty
     *
     * @param map    input {@link Map}
     * @param excStr Exception String supplied to IAE
     * @param <K>    key Type
     * @param <V>    value Type
     * @param <R>    Map<Key, Value>
     * @return {@code map } if passed validation successfully
     * @throws IllegalArgumentException if {@code map} is null or empty
     */
    public static <K, V, R extends Map<K, V>> R checkMapNotEmpty(R map, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }
        return map;
    }

    /**
     * Validate that Map is not null and contains supplied keys
     *
     * @param map {@link Map} to validate
     * @param keySet {@link Set} keys to check for
     * @param excStr Exception String supplied to IAE
     * @throws IllegalArgumentException if {@code map} is null or does not have keys
     * @return {@code map} if passed validation successfully
     * @param <K> key Type
     * @param <V> value Type
     */
    public static <K, V> Map<K, V> checkMapHasKeys(Map<K, V> map, Set<K> keySet, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);
        checkMapNotEmpty(map, excStr);
        checkCollectionIsNotEmpty(keySet, excStr);

        if (!map.keySet().containsAll(keySet)) {
            throw new IllegalArgumentException(excStr);
        }
        return map;
    }

    /**
     * Validate that CharSequence is not null or blank
     *
     * @param val    {@link  CharSequence} to validate
     * @param excStr Exception String supplied to IAE
     * @param <R>    input {@link CharSequence}
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is null or blank
     */
    public static <R extends CharSequence> R checkNotBlank(R val, String excStr) {
        if (StringUtils.isBlank(excStr))
            throw new IllegalArgumentException(EXC_STR_MISSING_OR_EMPTY);

        if (StringUtils.isBlank(val))
            throw new IllegalArgumentException(excStr);
        else return val;
    }

    /**
     * Validate that double {@code number} is not null and positive
     *
     * @param number    to validate
     * @param excSubStr Exception Substring
     * @param <R>       type of {@link Number}
     * @return {@code number} if passed validation successfully
     * @throws IllegalArgumentException if {@code number} is not positive
     */
    public static <R extends Number> R checkIsPositive(R number, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkNotNull(number, excSubStr);

        if (number instanceof Double)
            checkArgument(number.doubleValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof Long)
            checkArgument(number.longValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof Float)
            checkArgument(number.floatValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof Integer)
            checkArgument(number.intValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof Short)
            checkArgument(number.shortValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof Byte)
            checkArgument(number.byteValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else {
            throw new IllegalArgumentException("Cannot determine if Number is positive");
        }

        return number;
    }

    /**
     * Validate that Collection is not null or empty
     *
     * @param collection input (@link Collection}
     * @param excStr     Exception String supplied to IAE
     * @param <T>        type contained in Collection
     * @param <R>        Collection of a Type
     * @return {@code collection} if passed validation successfully
     * @throws IllegalArgumentException if {@code collection} is null or empty
     */
    public static <T, R extends Collection<T>> R checkCollectionIsNotEmpty(R collection, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }
        return collection;
    }

    /**
     * Validate that value is not null
     *
     * @param val    input Object
     * @param excStr Exception String supplied to IAE
     * @param <T>    object Type
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is null
     */
    public static <T> T checkNotNull(T val, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (val == null) {
            throw new IllegalArgumentException(excStr);
        }
        return val;
    }

    /**
     * Validate that Array is not null or empty
     *
     * @param array  to validate
     * @param excStr Exception String supplied to IAE
     * @param <T>    array Type
     * @return {@code array} if passed validation successfully
     * @throws IllegalArgumentException if {@code array} is null or empty
     */
    public static <T> T[] checkArrayNotEmpty(T[] array, String excStr) {
        checkNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(!ArrayUtils.isEmpty(array), excStr);
        return array;
    }

    /**
     * Validate that byte {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static byte checkIsNotNegative(byte val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that byte {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static byte checkIsPositive(byte val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that {@code val} is greater than {@code minimum}
     *
     * @param val       to validate
     * @param minimum   to check against
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     */
    public static double checkIsGreaterThan(double val, double minimum, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > minimum, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that double {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static double checkIsNotNegative(double val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that double {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static double checkIsPositive(double val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that float {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static float checkIsNotNegative(float val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that float {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static float checkIsPositive(float val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that {@code val} is greater than {@code minimum}
     *
     * @param val       to validate
     * @param minimum   to check against
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException {@code val} is not greater than {@code number}
     */
    public static int checkIsGreaterThan(int val, int minimum, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val <= minimum, String.format(excSubStr, val));
        return val;
    }

    /**
     * Validate that long {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static long checkIsNotNegative(long val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that long {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static long checkIsPositive(long val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that int {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static int checkIsNotNegative(int val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that int {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static int checkIsPositive(int val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that short {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is negative
     */
    public static short checkIsNotNegative(short val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that short {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalArgumentException if {@code val} is not positive
     */
    public static short checkIsPositive(short val, String excSubStr) {
        checkNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /*
       Validations prefixed with require represent checks against Illegal State in an Application
     */

    /**
     * Validate that int {@code val} is within inclusive range of (@code from} - {@code to}
     *
     * @param val       to validate
     * @param from      beginning of range, inclusive
     * @param to        beginning of range, inclusive
     * @param excSubStr Exception Substring supplied to IAE
     * @throws IllegalArgumentException if {@code val} is not within range of {@code from} - {@code to}
     */
    public static void checkIsWithinClosedIntegerRange(int val, int from, int to, String excSubStr) {
        checkNotNull(excSubStr, EXC_SUBSTR_MISSING_OR_EMPTY);

        if (!Range.closed(from, to).contains(val))
            throw new IllegalArgumentException(String.format(EXC_STR_WITHIN_RANGE, val, from, to));
    }

    /**
     * Validate that Map is not null and contains keys
     *
     * @param map    {@link Map} to validate
     * @param excStr Exception String supplied to IAE
     * @param keys   keys to check for
     * @param <K>    key Type
     * @param <V>    value Type
     * @return {@code map} if passed validation successfully
     * @throws IllegalStateException if {@code map} is null or does not have keys
     */
    @SafeVarargs
    public static <K, V> Map<K, V> requireMapHasKeys(Map<K, V> map, String excStr, K... keys) {
        val set = Stream.of(requireArrayNotEmpty(keys, excStr))
                .collect(Collectors.toUnmodifiableSet());
        return requireMapHasKeys(map, set, excStr);
    }

    /**
     * Validate that Map is not null or empty
     *
     * @param map    input {@link Map}
     * @param excStr Exception String supplied to IAE
     * @param <K>    key Type
     * @param <V>    value Type
     * @param <R>    Map<Key, Value>
     * @return {@code map } if passed validation successfully
     * @throws IllegalStateException if {@code map} is null or empty
     */
    public static <K, V, R extends Map<K, V>> R requireMapNotEmpty(R map, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (map == null || map.isEmpty()) {
            throw new IllegalStateException(excStr);
        }
        return map;
    }

    /**
     * Validate that Map is not null and contains supplied keys
     *
     * @param map    {@link Map} to validate
     * @param keySet {@link Set} keys to check for
     * @param excStr Exception String supplied to IAE
     * @param <K>    key Type
     * @param <V>    value Type
     * @return {@code map} if passed validation successfully
     * @throws IllegalStateException if {@code map} is null or does not have keys
     */
    public static <K, V> Map<K, V> requireMapHasKeys(Map<K, V> map, Set<K> keySet, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);
        requireMapNotEmpty(map, excStr);
        requireCollectionIsNotEmpty(keySet, excStr);

        if (!map.keySet().containsAll(keySet)) {
            throw new IllegalStateException(excStr);
        }
        return map;
    }

    /**
     * Validate that CharSequence is not null or blank
     *
     * @param val    {@link  CharSequence} to validate
     * @param excStr Exception String supplied to IAE
     * @param <R>    input {@link CharSequence}
     * @return {@code val} if passed validation successfully
     * @throws IllegalStateException if {@code val} is null or blank
     */
    public static <R extends CharSequence> R requireNotBlank(R val, String excStr) {
        if (StringUtils.isBlank(excStr))
            throw new IllegalStateException(EXC_STR_MISSING_OR_EMPTY);

        if (StringUtils.isBlank(val))
            throw new IllegalStateException(excStr);
        else return val;
    }

    /**
     * Validate that Collection is not null or empty
     *
     * @param collection input (@link Collection}
     * @param excStr     Exception String supplied to IAE
     * @param <T>        type contained in Collection
     * @param <R>        Collection of a Type
     * @return {@code collection} if passed validation successfully
     * @throws IllegalStateException if {@code collection} is null or empty
     */
    public static <T, R extends Collection<T>> R requireCollectionIsNotEmpty(R collection, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (collection == null || collection.isEmpty()) {
            throw new IllegalStateException(excStr);
        }
        return collection;
    }

    /**
     * Validate that Array is not null or empty
     *
     * @param array  to validate
     * @param excStr Exception String supplied to IAE
     * @param <T>    array Type
     * @return {@code array} if passed validation successfully
     * @throws IllegalStateException if {@code array} is null or empty
     */
    public static <T> T[] requireArrayNotEmpty(T[] array, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);
        if (ArrayUtils.isEmpty(array))
            throw new IllegalStateException(excStr);
        return array;
    }

    /**
     * Validate that int {@code val} is not negative
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalStateException if {@code val} is negative
     */
    public static int requireNotNegative(int val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        if (val < 0)
            throw new IllegalStateException(String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    /**
     * Validate that int {@code val} is positive
     *
     * @param val       to validate
     * @param excSubStr Exception Substring supplied to IAE
     * @return {@code val} if passed validation successfully
     * @throws IllegalStateException if {@code val} is not positive
     */
    public static int requirePositive(int val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        if (val <= 0)
            throw new IllegalStateException(String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    /**
     * Validate that T {@code obj} is not null;
     *
     * @param obj       to validate
     * @param excSubStr Exception String supplied to NPE
     * @param <T>       input obj type
     * @return {@code obj} if passed validation successfully
     * @throws NullPointerException  is {@code obj} is missing
     * @throws IllegalStateException is {@code excStr} is missing or empty
     */
    public static <T> T requireNotNull(T obj, String excSubStr) {
        requireNotBlank(excSubStr, EXC_SUBSTR_MISSING_OR_EMPTY);
        if (obj == null) {
            throw new NullPointerException(String.format(IS_REQD_AND_MISSING, excSubStr));
        }

        return obj;
    }

    private Preconditions() {
    }

    /**
     * Validates that ExecutorService {@code executor} is not null
     *
     * @param executor to validate
     * @return {@code executor} if passed validation successfully
     */
    public static ExecutorService requireExecutorService(ExecutorService executor) {
        return requireNotNull(executor, "executor");
    }
}

package com.onyx.onyxapi.commons.util;

import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;

/* Validates input for legal arguments */
public final class Preconditions {

    private static final String EXC_STR_MUST_BE_POSITIVE = "%s must be positive";
    private static final String EXC_STR_MISSING_OR_EMPTY = "excStr is required and is missing or blank";
    private static final String EXC_STR_MUST_NOT_BE_NEGATIVE = "%s must be negative";

    /**
     * Validate that CharSequence is not null or blank
     *
     * @param val {@link  CharSequence} to validate
     * @param excStr Exception String supplied to IAE
     * @throws IllegalArgumentException if {@code val} is null or blank
     * @return {@code val}
     * @param <R> input {@link CharSequence}
     */
    public static <R extends CharSequence> R requireNotBlank(R val, String excStr) {
        if(StringUtils.isBlank(excStr))
            throw new IllegalArgumentException(EXC_STR_MISSING_OR_EMPTY);

        if(StringUtils.isBlank(val))
            throw new IllegalArgumentException(excStr);
        else return val;
    }

    /**
     * Validate that Map is not null and contains keys
     *
     * @param map {@link Map} to check
     * @param keySet {@link Set} keys to check for
     * @param excStr Exception String supplied to IAE
     * @throws IllegalArgumentException if {@code map} is null or does not have keys
     * @return {@code map}
     * @param <K> key Type
     * @param <V> value Type
     */
    public static <K, V> Map<K, V> requireMapHasKeys(Map<K, V> map, Set<K> keySet, String excStr) {
        requireMapNotEmpty(map, excStr);
        requireCollectionNotEmpty(keySet, excStr);
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (!map.keySet().containsAll(keySet)){
            throw new IllegalArgumentException(excStr);
        }
        return map;
    }

    /**
     * Validate that Map is not null and contains keys
     *
     * @param map {@link Map} to check
     * @param excStr Exception String supplied to IAE
     * @param keys keys to check for
     * @throws IllegalArgumentException if {@code map} is null or does not have keys
     * @return {@code map}
     * @param <K> key Type
     * @param <V> value Type
     */
    @SafeVarargs
    public static <K, V> Map<K, V> requireMapHasKeys(Map<K, V> map, String excStr, K ... keys) {
        //TODO - IntelliJ CodeSense complaint: Return value of the method is never used?
        val set = Stream.of(requireArrayNotEmpty(keys, excStr))
                .collect(Collectors.toUnmodifiableSet());
        return requireMapHasKeys(map, set, excStr);
    }

    /**
     * Validate that Array is not null or empty
     *
     * @param array to check
     * @param excStr Exception supplied to IAE
     * @throws IllegalArgumentException if {@code array} is null or empty
     * @return {@code array}
     * @param <T> array Type
     */
    public static <T> T[] requireArrayNotEmpty(T[] array, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(!ArrayUtils.isEmpty(array), excStr);
        return array;
    }

    /**
     * Validate that value is not null
     *
     * @param val input Object
     * @param excStr Exception supplied to IAE
     * @throws IllegalArgumentException if {@code val} is null
     * @return {@code val }
     * @param <T> object Type
     */
    public static <T> T requireArgNonNull(T val, String excStr) {
        //TODO - IntelliJ CodeSense complaint: Return value of the method is never used?
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (val==null) {
            throw new IllegalArgumentException(excStr);
        }
        return val;
    }

    /**
     * Validate that Map is not null or empty
     *
     * @param map input {@link Map}
     * @param excStr Exception supplied to IAE
     * @throws IllegalArgumentException if {@code map} is null or empty
     * @return {@code map }
     * @param <K> key Type
     * @param <V> value Type
     * @param <R> Map<Key, Value>
     */
    public static <K, V, R extends Map<K, V>> R requireMapNotEmpty (R map, String excStr) {
        //TODO - IntelliJ CodeSense complaint: Return value of the method is never used?
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }
        return map;
    }

    /**
     * Validate that Map is not null, empty,or contains a null value
     *
     * @param map input {@link Map}
     * @param excStr Exception supplied to IAE
     * @throws IllegalArgumentException if {@code map} is null, empty, or contains a null value
     * @return {@code map}
     * @param <K> key Type
     * @param <V> value Type
     * @param <R> Map<Key, Value>
     */
    public static <K, V, R extends Map<K, V>> R requireMapEntriesNotEmpty (R map, String excStr) {
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }

        if (map.containsValue(null)){
            throw new IllegalArgumentException(excStr);
        }

        return map;
    }

    /**
     * Validate that Collection is not null or empty
     *
     * @param collection input (@link Collection}
     * @param excStr Exception supplied to IAE
     * @return {@code collection}
     * @param <T> type contained in Collection
     * @param <R> Collection of a Type
     */
    public static <T, R extends Collection<T>> R requireCollectionNotEmpty (R collection, String excStr) {
        //TODO - IntelliJ CodeSense complaint: Return value of the method is never used?
        requireNotBlank(excStr, EXC_STR_MISSING_OR_EMPTY);

        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(excStr);
        }
        return collection;
    }

    //TODO - java docs

    public static double requireNonNegative(double val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static long requireNonNegative(long val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static float requireNonNegative(float val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static int requireNonNegative(int val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static short requireNonNegative(short val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static byte requireNonNegative(byte val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >= 0, String.format(EXC_STR_MUST_NOT_BE_NEGATIVE, excSubStr));
        return val;
    }

    public static double requirePositive(double val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }

    public static long requirePositive(long val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static float requirePositive(float val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static int requirePositive(int val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static short requirePositive(short val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static byte requirePositive(byte val, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static <R extends Number> R requirePositive(R number, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        requireArgNonNull(number, excSubStr);

        if (number instanceof Double)
            checkArgument(number.doubleValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof  Long)
            checkArgument(number.longValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof  Float)
            checkArgument(number.floatValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof  Integer)
            checkArgument(number.intValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof  Short)
            checkArgument(number.shortValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else if (number instanceof  Byte)
            checkArgument(number.byteValue() > 0, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        else {
            throw new IllegalArgumentException("Cannot determine if Number is positive");
        }

        return number;
    }
    public static int requireGreaterThan(int val, int minimum, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >minimum, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }
    public static double requireGreaterThan(double val, double minimum, String excSubStr) {
        requireNotBlank(excSubStr, EXC_STR_MISSING_OR_EMPTY);
        checkArgument(val >minimum, String.format(EXC_STR_MUST_BE_POSITIVE, excSubStr));
        return val;
    }


    private Preconditions () {}
}

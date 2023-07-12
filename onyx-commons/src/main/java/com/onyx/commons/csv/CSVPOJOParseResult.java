package com.onyx.commons.csv;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.onyx.commons.util.Preconditions.requireNotNull;

/**
 * Result Set of parsing POJO from CSV Stream using {@link CSVPOJOParser}
 *
 * @param <T>                    POJO type
 * @param parsedPOJOs            Successfully parsed POJOs from CSV file stream
 * @param csvRowToErrorResultSet Insertion Ordered details of failed row to POJO
 *                               parse attempts Mapping CSV Row to Exception.toString()
 */
public record CSVPOJOParseResult<T>(
        Collection<T> parsedPOJOs, CSVRowToErrorResultSet csvRowToErrorResultSet) {

    /**
     * Does Result Set have errors
     *
     * @return result of query
     */
    public boolean hasErrors() {
        return csvRowToErrorResultSet.isEmpty();
    }

    /**
     * Does Result Set have only Errors
     *
     * @return result of query
     */
    public boolean hasOnlyErrors() {
        return hasErrors() && !hasParsedPOJOs();
    }

    /**
     * Does Result Set Have Only POJOs
     *
     * @return result of query
     */
    public boolean hasOnlyParsedPOJOs() {
        return hasParsedPOJOs() && !hasErrors();
    }

    /**
     * Does Result Set have POJOs
     *
     * @return result of query
     */
    public boolean hasParsedPOJOs() {
        return !parsedPOJOs.isEmpty();
    }

    /**
     * DTO Encapsulating mapping of CSV Row Dara to Error String
     */
    public static final class CSVRowToErrorResultSet {
        private final Map<CSVTextRow, String> csvRowToError;

        public CSVRowToErrorResultSet(Map<CSVTextRow, String> csvRowToError) {
            requireNotNull(csvRowToError, "csvRowToError");
            this.csvRowToError = Collections.unmodifiableMap(new LinkedHashMap<>(csvRowToError));
        }

        /**
         * Whether this Result Set is empty
         *
         * @return result of query
         */
        public boolean isEmpty() {
            return !csvRowToError.isEmpty();
        }

        /**
         * Returns the Error String associated with this
         * CSV Row, which may be null
         *
         * @param csvRow target row in error result set
         * @return Error Text associated with a Row
         */
        @Nullable
        public String getErrorFromRow(CSVTextRow csvRow) {
            return csvRowToError.get(csvRow);
        }
    }
}

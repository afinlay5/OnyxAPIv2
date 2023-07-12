package com.onyx.commons.csv;

import de.siegmar.fastcsv.writer.CsvWriter;
import de.siegmar.fastcsv.writer.LineDelimiter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.LinkedList;
import java.util.List;

import static com.onyx.commons.util.Preconditions.checkCollectionIsNotEmpty;
import static com.onyx.commons.util.Preconditions.requireNotBlank;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_FIELD_DELIMITER;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_LINE_DELIMITER;

/**
 * Arranges {@link List} of String data as CSV
 *
 * <p>exporting to plain String
 */
public final class SimpleCSVStringWriter {

    // NOTE: Assumes Logical Order of Fluent Query

    /* CSV Writer Builder
     * with Quote Character("), Comment Character("#"),
     * Quote Strategy {@link QuoteStrategy.REQUIRED},
     * and Line Delimiter {@link LineDelimiter.CRLF}
     * ignoring comments */

    private final CsvWriter.CsvWriterBuilder csvWriterBuilder = CsvWriter.builder();

    /* Ordered CSV Row Data */
    private final List<String> orderedCsvRows;

    /* CSV Delimiter used to delimit fields in a row */
    private Character fieldDelimiter;

    /*CSV Delimiter used to delimit lines */
    private LineDelimiter lineDelimiter;

    private SimpleCSVStringWriter(List<String> orderedCsvRows) {
        this.orderedCsvRows = orderedCsvRows;
    }

    /**
     * Entry point of Fluent Interface
     *
     * <p>Supplies CSV Data to be written
     *
     * @param orderedCsvRows Ordered CSV Row Data
     * @return SimpleCSVStringWriter this
     */
    public static SimpleCSVStringWriter write(List<String> orderedCsvRows) {
        checkCollectionIsNotEmpty(orderedCsvRows, "CSV Data is required and missing");
        return new SimpleCSVStringWriter(new LinkedList<>(orderedCsvRows));
    }

    /**
     * Optional Intermediate Operation
     *
     * <p>Specifies Field Delimiter for writing CSV fields
     *
     * <p>CSV data will be delimited by ',' by default
     *
     * @param fieldDelimiter CSV Field Delimiter
     * @return SimpleCSVStringWriter this
     */
    public SimpleCSVStringWriter delimitingFieldsBy(char fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        return this;
    }

    /**
     * Optional Intermediate Operation
     * <p>Species Delimiter for Writing CSV Lines
     *
     * <p>CSV data will be delimited by
     *
     * <p>Carriage Return and Line Feed (CRLF) by default
     *
     * <p>Valid values are: "CRLF", "CR", "LF"
     *
     * @param lineDelimiter CSV Line Delimiter
     * @return SimpleCSVStringWriter this
     * @throws IllegalArgumentException if {@code delimiter} is null, blank, or invalid
     */
    public SimpleCSVStringWriter delimitingLinesBy(String lineDelimiter) {
        this.lineDelimiter = LineDelimiter.of(requireNotBlank(lineDelimiter, "delimiter is required but is missing"));
        return this;
    }

    /**
     * Terminal Operation
     *
     * <p>Takes ordered List of CSV Rows as String
     *
     * <p>and writes them in CSV format
     *
     * <p>with respect to supplied field delimiter (or the default)
     *
     * <p>with respect to supplied line delimiter (or the default)
     *
     * <p>exporting as String
     *
     * @return String CSV Data
     * @throws UncheckedIOException if an Error occurs processing file stream
     */
    public String toCSVString() {
        verifyOperationPreconditions();

        var fieldDelimiterStr = String.valueOf(fieldDelimiter);
        try (var csvStringWriter = new StringWriter(orderedCsvRows.size());
             var csvWriter =
                     csvWriterBuilder
                             .fieldSeparator(fieldDelimiter)
                             .lineDelimiter(lineDelimiter)
                             .build(csvStringWriter)) {
            orderedCsvRows.forEach(row -> csvWriter.writeRow(row.split(fieldDelimiterStr)));
            return csvStringWriter.toString();
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private void verifyOperationPreconditions() {
        if (orderedCsvRows == null) {
            throw new IllegalStateException(
                    "Required Intermediate Operation SimpleCSVStringWriter.write(...) was not invoked");
        }

        if (fieldDelimiter == null) {
            fieldDelimiter = DEFAULT_CSV_FIELD_DELIMITER;
        }

        if (lineDelimiter == null) {
            lineDelimiter = LineDelimiter.of(DEFAULT_CSV_LINE_DELIMITER);
        }
    }
}

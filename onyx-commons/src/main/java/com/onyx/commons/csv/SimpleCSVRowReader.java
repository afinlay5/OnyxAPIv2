package com.onyx.commons.csv;

import de.siegmar.fastcsv.reader.NamedCsvReader;
import de.siegmar.fastcsv.writer.LineDelimiter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.LinkedHashSet;
import java.util.List;

import static com.onyx.commons.util.Preconditions.checkNotBlank;
import static com.onyx.commons.util.Preconditions.checkNotNull;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_FIELD_DELIMITER;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_LINE_DELIMITER;


/**
 * Reads CSV from {@link Reader}
 *
 * <p>exports rows as {@link ResultSet}
 *
 * <p>containing Header to Row Value mappings,
 *
 * <p>ignoring CSV comments
 */
public final class SimpleCSVRowReader {

    /* CSV Reader Builder
     * with Quote Character(")
     * and Comment Character(#)
     * ignoring comments */
    private static final NamedCsvReader.NamedCsvReaderBuilder CSV_READER_BUILDER =
            NamedCsvReader.builder();

    // NOTE: Assumes Logical Order of Fluent Query
    /* Binary source of the CSV data*/
    private final Reader source;

    /* CSV Delimiter used to delimit fields in a Row*/
    private Character fieldDelimiter;

    /* CSV Delimiter used to delimit lines */
    private LineDelimiter lineDelimiter;

    private SimpleCSVRowReader(Reader source) {
        this.source = source;
    }

    /**
     * Entry point of fluent interface
     *
     * <p>Supplies CSV data source
     *
     * @param source data source to read from
     * @return new Instance of SimpleCSVRowReader
     */
    public static SimpleCSVRowReader from(Reader source) {
        checkNotNull(source, "Data Source is required and missing");
        try {
            var stringWriter = new StringWriter();
            source.transferTo(stringWriter);

            return new SimpleCSVRowReader(new StringReader(stringWriter.getBuffer().toString()));

        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    /**
     * Optional Intermediate Operation
     *
     * <p>Specifies Field Delimiter for writing CSV Fields
     *
     * <p> CSV Data will be delimited by ',' by default
     *
     * @param fieldDelimiter CSV Field Delimiter
     * @return SimpleCSVRowReader this
     */
    public SimpleCSVRowReader delimitingFieldsBy(char fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
        return this;
    }

    /**
     * Optional Intermediate Operation
     *
     * <p>Specifies delimiter for writing CSV lines
     *
     * <p>CSV data will be delimited by
     *
     * <p>Carriage Return and Line Feed (CRLF) by default
     *
     * <p>Valid values are: "CRLF", "CR", "LF
     *
     * @param delimiter CSV Line Delimiter
     * @return SimpleCSVRowReader this
     * @throws IllegalArgumentException if {@code delimiter} is null, blank, or invalid
     */
    public SimpleCSVRowReader delimitingLinesBy(String delimiter) {
        this.lineDelimiter = LineDelimiter.of(checkNotBlank(delimiter, "delimiter is required but is missing"));
        return this;
    }

    /**
     * Terminal Operation
     *
     * <p>Parses CSV data returning {@link ResultSet} or operation
     *
     * @return ResultSet containing headers and ordered map of csv row data
     * @throws UncheckedIOException if an Error occurs processing field stream
     */
    public ResultSet getParseResult() {
        verifyOperationPreconditions();

        try (var csvReader = CSV_READER_BUILDER.fieldSeparator(fieldDelimiter).build(source)) {
            return new ResultSet(
                    new LinkedHashSet<>(csvReader.getHeader()),
                    csvReader.stream().map(namedCsvRow -> new CSVTextRow(namedCsvRow.getFields())).toList());
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    private void verifyOperationPreconditions() {
        if (source == null) {
            throw new IllegalStateException("Required Intermediate Operation SimpleCSVRowReader.from(...) was not invoked");
        }

        if (fieldDelimiter == null) {
            fieldDelimiter = DEFAULT_CSV_FIELD_DELIMITER;
        }

        if (lineDelimiter == null) {
            lineDelimiter = LineDelimiter.of(DEFAULT_CSV_LINE_DELIMITER);
        }
    }

    /**
     * DTO containing parsed headers and row data
     *
     * @param header
     * @param rows
     */
    public record ResultSet(LinkedHashSet<String> header, List<CSVTextRow> rows) {
    }
}

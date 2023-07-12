package com.onyx.service.validation;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.mime.MimeTypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static com.onyx.commons.util.Preconditions.checkNotNull;
import static com.onyx.commons.util.TextProcessingHelper.CSV_FILE_EXT;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_MEDIA_TYPE;

/**
 * Utility for Validating {@link InputStream} as valid CSV File Stream
 */
public final class CSVInputStreamValidationUtil {

    private static final Metadata CSV_METADATA = new Metadata();

    static {
        CSV_METADATA.set(TikaCoreProperties.RESOURCE_NAME_KEY, CSV_FILE_EXT);
    }

    private CSVInputStreamValidationUtil() {
    }

    /**
     * Validates {@link InputStream} contains CSV bytes
     *
     * @param inputStream Input Stream arg
     * @return InputStream Input Arg, if validation is successful
     * @throws IllegalArgumentException if {@code inputStream} is missing
     * @throws UncheckedIOException     if an error occurs reading from {@code inputStream}
     */
    public static InputStream requireInputStreamAsValidCSVBytes(InputStream inputStream) {
        checkNotNull(inputStream, "Input Stream");
        try {
            if (!MimeTypes.getDefaultMimeTypes()
                    .detect(inputStream, CSV_METADATA)
                    .equals(DEFAULT_CSV_MEDIA_TYPE)) {
                throw new IllegalArgumentException("Input Stream bytes do not represent valid CSV");
            }
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        return inputStream;
    }
}

package com.onyx.commons.util;

import org.apache.tika.mime.MediaType;

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Optional;

/**
 * Helper containing common operations & constants for text processing
 */
public final class TextProcessingHelper {
    private static final String DEFAULT_CSV_CHARSET_STR = "UTF-8";
    public static final MediaType DEFAULT_CSV_MEDIA_TYPE = MediaType.parse(DEFAULT_CSV_CHARSET_STR);
    public static final String CSV_FILE_EXT = ".csv";
    public static final String DEFAULT_CSV_LINE_DELIMITER = "\r\n";
    public static final String DEFAULT_CSV_MIME_TYPE_STR = "text/csv";
    public static final char DEFAULT_CSV_FIELD_DELIMITER = ',';

    /**
     * Retrieves UTF-* Charset
     *
     * @return Charset UTF-8
     * @throws UnsupportedCharsetException if UTF-8 is not supported
     */
    public static Charset getDefaultCharacterEncoding() {
        return Optional.ofNullable(Charset.availableCharsets().get(DEFAULT_CSV_CHARSET_STR))
                .orElseThrow(() -> new UnsupportedCharsetException(DEFAULT_CSV_CHARSET_STR + " is not supported by this platform"));
    }

    private TextProcessingHelper() {
    }
}

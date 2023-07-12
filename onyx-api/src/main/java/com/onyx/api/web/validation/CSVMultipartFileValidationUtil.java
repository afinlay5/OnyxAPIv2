package com.onyx.api.web.validation;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;

import static com.onyx.commons.util.Preconditions.checkNotBlank;
import static com.onyx.commons.util.Preconditions.checkNotNull;
import static com.onyx.commons.util.TextProcessingHelper.CSV_FILE_EXT;
import static com.onyx.commons.util.TextProcessingHelper.DEFAULT_CSV_MIME_TYPE_STR;
import static com.onyx.service.validation.CSVInputStreamValidationUtil.requireInputStreamAsValidCSVBytes;

/**
 * Utility for Validating {@link MultipartFile} as valid CSV File Stream
 */
public final class CSVMultipartFileValidationUtil {

    private static final String REQUIRE_CSV_EXT_EXC =
            String.format("Multipart File Extension is not %s", CSV_FILE_EXT);

    private static final String REQUIRE_CSV_MIME_TYPE_EXT =
            String.format("Multipart File MIME Type is not %s", DEFAULT_CSV_MIME_TYPE_STR);

    private CSVMultipartFileValidationUtil() {
    }

    /**
     * Validates {@link MultipartFile} has a CSV File Extension
     *
     * @param multipartFile Multipart File to check
     * @return MultipartFile Input arg, if validation was successful
     * @throws IllegalArgumentException if {@code multipartFile} is missing
     */
    public static MultipartFile requireMultipartFileCsvExt(MultipartFile multipartFile) {
        checkNotNull(multipartFile, "Multipart File");
        var multipartFileOriginalFilename =
                checkNotBlank(
                        multipartFile.getOriginalFilename(),
                        "Multipart File's Original Filename is missing or blank");
        var csvFileExtIndx = multipartFileOriginalFilename.lastIndexOf(CSV_FILE_EXT);
        if (csvFileExtIndx == -1) {
            throw new IllegalArgumentException(REQUIRE_CSV_EXT_EXC);
        }

        if (!CSV_FILE_EXT.equalsIgnoreCase(multipartFileOriginalFilename.substring(csvFileExtIndx))) {
            throw new IllegalArgumentException(REQUIRE_CSV_EXT_EXC);
        }

        return multipartFile;
    }

    /**
     * Validates {@link MultipartFile} has a MIME type of CSV
     *
     * @param multipartFile Input Multipart File
     * @return MultipartFile Input Multipart File, if validation is successful
     * @throws IllegalArgumentException if {@code multipartFile} is missing
     */
    public static MultipartFile requireMultipartFileMIMETypeAsCsv(MultipartFile multipartFile) {
        checkNotNull(multipartFile, "Multipart File");
        var multipartFileContentType =
                checkNotBlank(
                        multipartFile.getContentType(), "Multipart File's Content Type is missing of blank");
        if (!DEFAULT_CSV_MIME_TYPE_STR.equalsIgnoreCase(multipartFileContentType.strip())) {
            throw new IllegalArgumentException(REQUIRE_CSV_MIME_TYPE_EXT);
        }

        return multipartFile;
    }

    /**
     * Validates {@link MultipartFile} contains CSV bytes
     *
     * @param multipartFile Input MultipartFile
     * @return MultipartFile Input Multipart File, if validation is successful
     * @throws IllegalArgumentException if {@code multipartFile} is missing
     */
    public static MultipartFile requireMultipartFileStreamAsValidCSVBytes(
            MultipartFile multipartFile) {
        checkNotNull(multipartFile, "MultipartFile");
        try (var multipartFileIS =
                     checkNotNull(multipartFile.getInputStream(), "Multipart File Stream")) {
            if (multipartFile.isEmpty()) {
                throw new IllegalArgumentException("Multipart File Stream is empty");
            }

            requireInputStreamAsValidCSVBytes(multipartFileIS);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }

        return multipartFile;
    }
}

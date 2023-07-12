package com.onyx.api.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onyx.commons.model.DataStoreDetails;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.onyx.commons.util.Preconditions.requireNotNull;

public final class JSONToDataStoreDetailsUtil {

    private JSONToDataStoreDetailsUtil() {
    }

    public static List<DataStoreDetails> parseDataStoreDetailsFromJsonResource(URL dataStoreJsonResource) {
        requireNotNull(dataStoreJsonResource, "dataStoreJsonResource");
        try (val jsonReader = new BufferedReader(new InputStreamReader(dataStoreJsonResource.openStream()))) {
            val typeToken = new TypeToken<ArrayList<DataStoreDetails>>() {
            }.getType();
            return new Gson().fromJson(jsonReader, typeToken);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }
}

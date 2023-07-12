package com.onyx.commons.csv;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.onyx.commons.util.Preconditions.requirePopulatedMap;

/**
 * Encapsulates CSV Header to Value Data
 */
public class CSVTextRow implements Iterable<Map.Entry<String, String>> {
    private final Map<String, String> headerToValueMapping;

    public CSVTextRow(Map<String, String> headerToValueMapping) {
        this.headerToValueMapping = new HashMap<>(requirePopulatedMap(
                headerToValueMapping, "headerToValueMapping is null or contains no mappings"));
    }

    /**
     * Returns text value corresponding to CSV column position,
     * which may be null
     *
     * @param header target column in CSV Row
     * @return row value corresponding to header
     */
    public String getValueByHeader(String header) {
        return headerToValueMapping.get(header);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return headerToValueMapping.entrySet().iterator();
    }

    /**
     * See {@link Map#putIfAbsent(Object, Object)}
     *
     * @param header Header
     * @param field  Field
     * @return result of {@link Map#putIfAbsent(Object, Object)}
     */
    public String addIfAbsent(String header, String field) {
        return headerToValueMapping.putIfAbsent(header, field);
    }

    /**
     * See {@link Map#put(Object, Object)}
     *
     * @param header Header
     * @param field  Field
     * @return result of {@link Map#put(Object, Object)}
     */
    public String add(String header, String field) {
        return headerToValueMapping.put(header, field);
    }

    /**
     * Get CSV Fields
     *
     * @return CSV Fields
     */
    public Collection<String> fields() {
        return headerToValueMapping.values();
    }

    /**
     * Get CSV Headers
     *
     * @return CSV Headers
     */
    public Set<String> headers() {
        return headerToValueMapping.keySet();
    }
}

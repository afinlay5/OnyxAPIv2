package com.onyx.commons.model.marshalling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.onyx.commons.model.BasketballStatisticsDataSource;
import lombok.val;

import java.io.IOException;

import static com.onyx.commons.util.Preconditions.checkNotBlank;
import static java.util.Objects.requireNonNull;

/**
 * Performs stripping and case-insensitive deserialization of {@link BasketballStatisticsDataSource}
 */
public class BasketballStatisticsDataSourceDeserializer extends JsonDeserializer<BasketballStatisticsDataSource> {

    /**
     * Method that can be called to ask implementation to deserialize
     * JSON content into the value type this serializer handles.
     * Returned instance is to be constructed by method itself.
     * <jsonParser>
     * Pre-condition for this method is that the parser points to the
     * first event that is part of value to deserializer (and which
     * is never JSON 'null' literal, more on this below): for simple
     * types it may be the only value; and for structured types the
     * Object start marker or a FIELD_NAME.
     * </jsonParser>
     * <jsonParser>
     * The two possible input conditions for structured types result
     * from polymorphism via fields. In the ordinary case, Jackson
     * calls this method when it has encountered an OBJECT_START,
     * and the method implementation must advance to the next token to
     * see the first field name. If the application configures
     * polymorphism via a field, then the object looks like the following.
     * <pre>
     *      {
     *          "@class": "class name",
     *          ...
     *      }
     *  </pre>
     * Jackson consumes the two tokens (the <tt>@class</tt> field name
     * and its value) in order to learn the class and select the deserializer.
     * Thus, the stream is pointing to the FIELD_NAME for the first field
     * after the @class. Thus, if you want your method to work correctly
     * both with and without polymorphism, you must begin your method with:
     * <pre>
     *       if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
     *         jsonParser.nextToken();
     *       }
     *  </pre>
     * This results in the stream pointing to the field name, so that
     * the two conditions align.
     * <jsonParser>
     * Post-condition is that the parser will point to the last
     * event that is part of deserialized value (or in case deserialization
     * fails, event that was not recognized or usable, which may be
     * the same event as the one it pointed to upon call).
     * <jsonParser>
     * Note that this method is never called for JSON null literal,
     * and thus deserializers need (and should) not check for it.
     *
     * @param jsonParser             Parsed used for reading JSON content
     * @param deserializationContext Context that can be used to access information about
     *                               this deserialization activity.
     * @return Deserialized value
     */
    @Override
    public BasketballStatisticsDataSource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        requireNonNull(jsonParser, "jsonParser is required and missing");

        val jsonNode = jsonParser.getCodec().readTree(jsonParser);
        val dataSource = checkNotBlank(((TextNode) jsonNode).asText(), "BasketballStatisticsDataSource is required and missing");

        return BasketballStatisticsDataSource.fromFmtAgnosticString(dataSource);
    }
}

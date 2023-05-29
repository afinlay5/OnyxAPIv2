package com.onyx.onyxapi.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ProblemDetail;

import javax.annotation.Nonnull;
import java.net.URI;

/* RFC 7807 Compliant HTTP API Problem Details */
@RequiredArgsConstructor
@Value
public final class OnyxApiProblemDetail extends ProblemDetail {

    @Getter
    private final String additionalInformation;

    private OnyxApiProblemDetail(Builder builder) {
        setType(builder.type);
        setTitle(builder.title);
        setStatus(builder.status);
        setDetail(builder.detail);
        setInstance(builder.instance);
        additionalInformation = builder.additionalInformation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * {@code OnyxApiProblemDetail} builder static inner class.
     */
    public static final class Builder {
        private URI type;
        private String title;
        private int status;
        private String detail;
        private URI instance;
        private String additionalInformation;

        private Builder() {
        }

        /**
         * Sets the {@code type} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code type} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withType(@Nonnull URI val) {
            type = val;
            return this;
        }

        /**
         * Sets the {@code title} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code title} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withTitle(@Nonnull String val) {
            title = val;
            return this;
        }

        /**
         * Sets the {@code status} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code status} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withStatus(int val) {
            status = val;
            return this;
        }

        /**
         * Sets the {@code detail} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code detail} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withDetail(@Nonnull String val) {
            detail = val;
            return this;
        }

        /**
         * Sets the {@code instance} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code instance} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withInstance(@Nonnull URI val) {
            instance = val;
            return this;
        }

        /**
         * Sets the {@code instance} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code instance} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withAdditionalInformation(@Nonnull String val) {
            additionalInformation = val;
            return this;
        }

        /**
         * Returns a {@code OnyxApiProblemDetail} built from the parameters previously set.
         *
         * @return a {@code OnyxApiProblemDetail} built with parameters of this {@code OnyxApiProblemDetail.Builder}
         */
        @Nonnull
        public OnyxApiProblemDetail build() {
            return new OnyxApiProblemDetail(this);
        }
    }
}

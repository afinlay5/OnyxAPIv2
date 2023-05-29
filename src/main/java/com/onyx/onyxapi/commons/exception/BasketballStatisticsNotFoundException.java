package com.onyx.onyxapi.commons.exception;

import lombok.Getter;

import javax.annotation.Nonnull;

public class BasketballStatisticsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Getter
    private final String title;
    @Getter
    private final String detail;
    @Getter
    private final String additionalInformation;

    private BasketballStatisticsNotFoundException(Builder builder) {
        title = builder.title;
        detail = builder.detail;
        additionalInformation = builder.additionalInformation;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * {@code BasketballStatisticsNotFoundException} builder static inner class.
     */
    public static final class Builder {
        private String title;
        private String detail;
        private String additionalInformation;

        private Builder() {
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
         * Sets the {@code additionalInformation} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code additionalInformation} to set
         * @return a reference to this Builder
         */
        @Nonnull
        public Builder withAdditionalInformation(@Nonnull String val) {
            additionalInformation = val;
            return this;
        }

        /**
         * Returns a {@code BasketballStatisticsNotFoundException} built from the parameters previously set.
         *
         * @return a {@code BasketballStatisticsNotFoundException} built with parameters of this {@code BasketballStatisticsNotFoundException.Builder}
         */
        @Nonnull
        public BasketballStatisticsNotFoundException build() {
            return new BasketballStatisticsNotFoundException(this);
        }
    }
}

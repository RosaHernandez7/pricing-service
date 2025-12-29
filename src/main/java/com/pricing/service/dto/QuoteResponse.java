package com.pricing.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse {
    @JsonProperty("Global Quote")
    private GlobalQuote globalQuote;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GlobalQuote{
        @JsonProperty("01. symbol")
        private String symbol;

        @JsonProperty("05. price")
        private String price;
    }

}
package com.pricing.service.service;

import com.pricing.service.dto.QuoteResponse;
import com.pricing.service.dto.SimpleQuoteRequest;
import com.pricing.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Slf4j
public class MarketClient {

    private final WebClient webClient;
    public MarketClient (WebClient webClient){
        this.webClient=webClient;
    }

    public Mono<SimpleQuoteRequest> getQuote(String symbol) {
        log.info("Entrando al MarketClient con symbol = " + symbol);

            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function","GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", "TU_API_KEY")
                        .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> Mono.error(new ExternalClientException("Error 4xx del proveedor")))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> Mono.error(new ExternalServerException("Error 5xx del proveedor")))
                    .bodyToMono(QuoteResponse.class)
                    .map(r -> new SimpleQuoteRequest(
                            r.getGlobalQuote().getSymbol(),
                            Double.parseDouble(r.getGlobalQuote().getPrice())))
                    .timeout(Duration.ofSeconds(5))
                    .onErrorResume(TimeoutException.class, ex ->
                            Mono.error(new ProviderTimeoutException("El proveedor tardó demasiado"))
                    );

    }
}

package com.pricing.service.controller;

import com.pricing.service.dto.SimpleQuoteRequest;
import com.pricing.service.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(PricingController.class)
class QuoteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private QuoteService quoteService;

    @Test
    void shouldReturn200() {

        SimpleQuoteRequest quote =
                new SimpleQuoteRequest("AAPL", 272.19);

        when(quoteService.getQuote("AAPL"))
                .thenReturn(Mono.just(quote));

        webTestClient.get()
                .uri("/api/quotes/AAPL")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }
}

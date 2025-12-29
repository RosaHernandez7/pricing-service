package com.pricing.service.service;

import com.pricing.service.dto.SimpleQuoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.EnableCaching;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when; //este ya acepta el nulo


@SpringBootTest
@Slf4j
@EnableCaching
class QuoteServiceTest {

    @MockBean
    private MarketClient marketClient;

    @Autowired
    private QuoteService quoteService;

    @Test
    void shouldUseCache() {
        SimpleQuoteRequest response = new SimpleQuoteRequest("AAPL", 272.19);

        when(marketClient.getQuote("AAPL"))
            .thenReturn(Mono.just(response));

        log.info("Primera llamada → proveedor");
        StepVerifier.create(quoteService.getQuote("AAPL"))
                .expectNext(response)
                .verifyComplete();

        log.info("Segunda llamada → cache");
        StepVerifier.create(quoteService.getQuote("AAPL"))
                .expectNext(response)
                .verifyComplete();

        verify(marketClient, times(1))
                .getQuote("AAPL");
    }
}

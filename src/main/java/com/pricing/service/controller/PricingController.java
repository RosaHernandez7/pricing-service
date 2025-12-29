package com.pricing.service.controller;

import com.pricing.service.dto.SimpleQuoteRequest;
import com.pricing.service.service.QuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/quotes")
public class PricingController {
    private final QuoteService quoteService;

    public PricingController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/{symbol}")
    public Mono<ResponseEntity<SimpleQuoteRequest>> getQuote(@PathVariable String symbol){
        return quoteService.getQuote(symbol)
                .map(ResponseEntity::ok)
                .onErrorResume(e ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build())
                );
    }

    @GetMapping("/test/{symbol}")
    public Mono<String> test(@PathVariable String symbol){
        return Mono.just("OK" + symbol);
    }

    @DeleteMapping("/quotes/{symbol}/cache")
    public Mono<Void> evict(@PathVariable String symbol) {
        quoteService.evictQuote(symbol);
        return Mono.empty();
    }
}

/*
* @RestController - see usa para indicar que la clase tendra endpoints
* @RequestMapping - declara la ruta inicial del consumo
* @PathVariable - entrada de datos pero url o por json???
* ResponseEntity -- regresar un mensaje HTTP
* */
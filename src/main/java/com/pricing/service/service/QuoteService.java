package com.pricing.service.service;

import com.pricing.service.dto.SimpleQuoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class QuoteService {

    private final MarketClient marketClient;

    public QuoteService(MarketClient marketClient) {
        this.marketClient = marketClient;
    }

    @Cacheable(value = "quotes", key = "#symbol")
    public Mono<SimpleQuoteRequest> getQuote(String symbol) {

        if (symbol == null || symbol.isBlank()) {
            return Mono.error(new IllegalArgumentException("El símbolo es obligatorio"));
        }

        log.info("Llamando al proveedor externo para {}", symbol);

        return marketClient.getQuote(symbol)
                .cache(); // 👈 cache REACTIVO
    }

    @CacheEvict(value = "quotes", key = "#symbol")
    public void evictQuote(String symbol) {
        log.info("🧹 Cache invalidado para {}", symbol);
    }
}

/*
* @Cacheable --> Guarda el resultado la primera vez y luego lo reutiliza
* Si el metodo se vuelve a llamar con el mismo symbol, NO se ejecuta el metodo,
* se devuelve el valor del caché.
* Se usa bajo las siguientes condiciones:
* Spring usa proxies → solo metodos public
* No tiene sentido cachear algo sin retorno
* Cachea consultas, no procesos
* No usar caché si:
* Datos cambian cada segundo
* Transacciones críticas (saldos, pagos)
* Datos dependientes de usuario sin key correcta
* @CacheEvict--> Elimina datos del caché
* */
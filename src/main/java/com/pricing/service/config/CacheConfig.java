package com.pricing.service.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("quotes");

        manager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(5))
                        .maximumSize(100)
        );

        // ATENCIÓN!!!
        manager.setAsyncCacheMode(true);

        return manager;
    }
}

/* por qué implementar en async mode ?
*   Porque WebFlux trabaja de forma NO bloqueante, y Spring Cache por defecto es bloqueante.
*   @EnableCaching--> sirve para activar el mecanismo de caché de Spring y permitir que anotaciones como @Cacheable,
*   @CachePut y @CacheEvict funcionen en tu aplicación, con la anotación:
    * Activa AOP (proxies) para interceptar metodos
    * Detecta anotaciones de caché
    * Guarda / recupera resultados automáticamente desde un CacheManager
    * Se declara una vez en toda la aplicación
    * Cuando si usar:
    * 6️⃣ ¿Cuándo SÍ usar @EnableCaching?
    * Llamas APIs externas
    * Consultas repetitivas a BD
    * Datos semi-estáticos
    * Microservicios con alto tráfico
* */

/*
* Tipo de errores: No Caffeine AsyncCache available
* */
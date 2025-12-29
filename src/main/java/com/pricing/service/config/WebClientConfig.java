package com.pricing.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://www.alphavantage.co")
                .build();
    }
}

/*
* @Configuration - una clase define configuracion del contenedor , equivale al applicationContext.xml
* “Esta clase define cómo se construyen los beans de la aplicación
* @Bean le dice a Spring: Este metodo crea un objeto que debe vivir en el contenedor de Springx
* La inyección de dependencias ocurre cuando Spring entrega ese bean a otra clase.
* */
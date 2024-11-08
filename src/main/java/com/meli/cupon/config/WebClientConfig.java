package com.meli.cupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuración de WebClient.
 * 
 * Esta clase define el bean `WebClient.Builder` que se utiliza para crear instancias de `WebClient`. 
 * `WebClient` es una alternativa más moderna y no bloqueante a `RestTemplate`, y se usa para hacer 
 * solicitudes HTTP de manera asincrónica.
 */
@Configuration
public class WebClientConfig {
    
    /**
     * Define un bean `WebClient.Builder` que se usa para construir instancias de `WebClient`.
     * 
     * @return Un objeto `WebClient.Builder` que se usa para construir instancias de `WebClient`.
     */
    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }
}

package com.meli.cupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de la aplicación.
 * 
 * Esta clase se encarga de la configuración de beans que son necesarios para el funcionamiento 
 * general de la aplicación. En este caso, se define un bean para el `RestTemplate`, que es una 
 * herramienta de Spring para realizar solicitudes HTTP hacia otros servicios de manera sincrónica.
 */
@Configuration
public class ApplicationConfig {

    /**
     * Define el bean `RestTemplate` que se utiliza para hacer llamadas HTTP de manera sincrónica.
     * 
     * El `RestTemplate` es una forma simple y rápida de realizar solicitudes RESTful a otros servicios 
     * desde la aplicación. En este caso, se usa para realizar las llamadas a otros servicios REST 
     * necesarios para la API.
     * 
     * @return Un objeto `RestTemplate` que permite hacer solicitudes HTTP.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

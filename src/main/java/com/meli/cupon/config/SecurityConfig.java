package com.meli.cupon.config;

import com.meli.cupon.security.JwtAuthenticationFilter;
import com.meli.cupon.security.JwtTokenProvider;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuración de seguridad de la aplicación.
 * 
 * Esta clase define cómo se manejará la seguridad en la aplicación. Se utiliza para configurar
 * la autenticación y autorización de los usuarios, usando *JWT* para asegurar los endpoints.
 * También se gestiona la política de CORS para permitir que la API sea consumida por otras 
 * aplicaciones de diferentes dominios.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define el bean para la clase `JwtTokenProvider`, que se encarga de la generación y validación 
     * de los tokens JWT utilizados para la autenticación en la API.
     * 
     * @return Una instancia de `JwtTokenProvider`.
     */
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider();
    }

    /**
     * Configura la cadena de filtros de seguridad, que establece cómo se deben manejar las solicitudes HTTP
     * y la autenticación de los usuarios mediante el uso de **JWT**.
     * 
     * Se desactiva la protección CSRF, se habilita CORS, y se establece una política de sesión sin estado
     * (stateless), lo que significa que no se gestionará información de sesión entre las solicitudes.
     * 
     * @param http La instancia de `HttpSecurity` que permite configurar la seguridad de la aplicación.
     * @return La instancia de `SecurityFilterChain` que configura los filtros de seguridad para la aplicación.
     * @throws Exception Si ocurre un error durante la configuración de la seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**","/auth/login").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .httpBasic();

        return http.build();
    }

    /**
     * Configura la seguridad para las rutas que no requieren autenticación.
     * En este caso, se configura para excluir de la seguridad los endpoints
     * relacionados con Swagger y la documentación de la API.
     * 
     * Esto se hace para que cualquier usuario, incluso sin autenticación, pueda acceder
     * a la interfaz de usuario de Swagger y a la documentación generada
     * de la API, lo cual es útil durante el desarrollo y las pruebas de la API.
     * 
     * @return Una instancia de `WebSecurityCustomizer` que personaliza la configuración
     *         de seguridad para las rutas no protegidas, permitiendo acceso público a
     *         los endpoints de Swagger y la documentación de la API.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**");

    }
    
    /**
     * Configura las opciones de CORS para permitir que la API sea consumida desde otros dominios.
     * 
     * En este caso, se permite el acceso solo desde un dominio específico y se especifican los métodos
     * HTTP y encabezados permitidos.
     * 
     * @return La configuración de CORS que define las políticas de acceso entre dominios.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://meli-api-coupon.wl.r.appspot.com/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

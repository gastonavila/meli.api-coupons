package com.meli.cupon.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación basado en JWT.
 * 
 * Este filtro intercepta las solicitudes HTTP para verificar si contienen un token JWT válido 
 * en la cabecera **Authorization**. Si el token es válido, se establece la autenticación en el contexto 
 * de seguridad de Spring, permitiendo que el usuario acceda a los recursos protegidos.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Filtra cada solicitud para verificar la validez del token JWT.
     * 
     * Si la solicitud contiene un token JWT válido en la cabecera **Authorization** (con el prefijo 
     * "Bearer "), el filtro valida el token y extrae la información del usuario, estableciendo la 
     * autenticación en el contexto de seguridad.
     * 
     * @param request La solicitud HTTP entrante.
     * @param response La respuesta HTTP que se enviará.
     * @param filterChain La cadena de filtros.
     * @throws IOException Si ocurre un error en el procesamiento de la solicitud.
     * @throws ServletException Si ocurre un error al procesar la solicitud.
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); //

            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsername(token);
                Authentication auth = jwtTokenProvider.getAuthentication(username);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
package com.meli.cupon.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Proveedor de tokens JWT.
 * 
 * Esta clase se encarga de la creación, validación y extracción de información de los tokens JWT. 
 * El proveedor utiliza una **clave secreta** para firmar y verificar la validez de los tokens, 
 * así como para extraer el nombre de usuario del token.
 */
@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "your_secret_key";
    private static final long VALIDITY_IN_MILLISECONDS = 3600000;

    @Value("${jwt.username}")
    private String username;

    @Value("${jwt.password}")
    private String password;
    
    /**
     * Crea un token JWT basado en el nombre de usuario.
     * 
     * @param username El nombre de usuario para el cual se genera el token.
     * @return Un token JWT firmado y válido por un tiempo limitado(1 hora).
     */
    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_IN_MILLISECONDS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Valida un token JWT comparando las credenciales del usuario.
     * 
     * @param userInputUsername El nombre de usuario ingresado.
     * @param userInputPassword La contraseña ingresada.
     * @return **true** si las credenciales son válidas, **false** en caso contrario.
     */
    public boolean validateUser(String userInputUsername, String userInputPassword) {
        return username.equals(userInputUsername) && password.equals(userInputPassword);
    }
    
    /**
     * Extrae el nombre de usuario del token JWT.
     * 
     * @param token El token JWT a validar.
     * @return El nombre de usuario extraído del token.
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida la integridad y la firma de un token JWT.
     * 
     * @param token El token JWT a validar.
     * @return **true** si el token es válido, **false** si ha expirado o es incorrecto.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtiene la autenticación del usuario a partir del token JWT.
     * 
     * @param username El nombre de usuario para crear una autenticación.
     * @return Un objeto **Authentication** con los roles del usuario.
     */
    public Authentication getAuthentication(String username) {
            UserDetails userDetails = User.withUsername(username)
                .authorities(new SimpleGrantedAuthority("USER"))
                .password("")
                .build();
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
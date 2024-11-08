package com.meli.cupon.service;

import com.meli.cupon.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación para gestionar el inicio de sesión de los usuarios.
 * 
 * Esta clase es responsable de autenticar a los usuarios utilizando el **nombre de usuario** y la **contraseña** 
 * proporcionada. Si las credenciales son válidas, se genera un token **JWT** para el usuario, de lo contrario, 
 * se lanza una excepción **BadCredentialsException**.
 */
@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Realiza el proceso de autenticación.
     * 
     * Verifica si las credenciales proporcionadas son válidas. Si es así, genera y devuelve un token **JWT** 
     * para el usuario. Si las credenciales son incorrectas, se lanza una **BadCredentialsException**.
     * 
     * @param username El nombre de usuario ingresado por el usuario.
     * @param password La contraseña proporcionada por el usuario.
     * @return Un token JWT que autentica al usuario.
     * @throws BadCredentialsException Si las credenciales son inválidas.
     */
    public String login(String username, String password) {
        if (!jwtTokenProvider.validateUser(username, password)) {
            throw new BadCredentialsException("Credenciales Inválidas");
        }
        return jwtTokenProvider.createToken(username);
    }
}


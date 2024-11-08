package com.meli.cupon.controller;

import com.meli.cupon.model.response.AuthRequest;
import com.meli.cupon.security.JwtTokenProvider;
import com.meli.cupon.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la autenticación de usuarios.
 * 
 * Este controlador maneja las solicitudes relacionadas con el inicio de sesión y la obtención 
 * de un **token JWT** para usuarios autenticados. Proporciona un endpoint para que los usuarios 
 * envíen sus credenciales y reciban un token que será usado para autorizar sus futuras solicitudes.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    /**
     * Endpoint para iniciar sesión en la API.
     * 
     * Este endpoint recibe un objeto **AuthRequest** con el nombre de usuario y la contraseña del usuario. 
     * Si las credenciales son válidas, devuelve un **token JWT** que se utiliza para autenticar al usuario 
     * en futuras solicitudes a la API.
     * 
     * @param authRequest Objeto que contiene las credenciales del usuario (nombre de usuario y contraseña).
     * @return Un **token JWT** en caso de autenticación exitosa o una respuesta de error 401 si las credenciales son incorrectas.
     */
    @Operation(summary = "Iniciar sesión", description = "Autentica al usuario y devuelve un token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest.getUsername(), authRequest.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).build();
    }

}
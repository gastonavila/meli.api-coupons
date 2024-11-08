package com.meli.cupon;

import com.meli.cupon.controller.AuthController;
import com.meli.cupon.model.response.AuthRequest;
import com.meli.cupon.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;  // Mockeamos el AuthService

    @InjectMocks
    private AuthController authController;  // Inyectamos el mock en el controlador

    @Test
    void testLoginWithValidCredentials() {
        // Arrange: Simulamos que el servicio devuelve un token JWT
        String username = "test";
        String password = "12345";
        String expectedToken = "some.jwt.token";

        when(authService.login(username, password)).thenReturn(expectedToken);

        // Act: Llamamos al método del controlador
        AuthRequest req = new AuthRequest();
        req.setUsername(username);
        req.setPassword(password);
        ResponseEntity<String> response = authController.login(req);

        // Assert: Verificamos que el token está presente y que la respuesta sea correcta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange: Simulamos que el servicio devuelve null (credenciales incorrectas)
        String username = "test";
        String password = "wrongpassword";

        when(authService.login(username, password)).thenReturn(null);

        // Act: Llamamos al método del controlador
        AuthRequest req = new AuthRequest();
        req.setUsername(username);
        req.setPassword(password);
        ResponseEntity<String> response = authController.login(req);

        // Assert: Verificamos que la respuesta sea un 401 (Unauthorized)
        assertEquals(401, response.getStatusCodeValue());
    }
}

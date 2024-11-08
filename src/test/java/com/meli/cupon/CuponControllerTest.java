package com.meli.cupon;
import com.meli.cupon.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Especificamos un puerto aleatorio para las pruebas
@AutoConfigureMockMvc // Habilitamos el soporte de MockMvc
public class CuponControllerTest {

    @Autowired
    private MockMvc mockMvc; // Utilizamos MockMvc para realizar las peticiones HTTP

    @Autowired
    private AuthService authService; // Servicio para obtener el token JWT

    @Test
    void testGetCouponItems() throws Exception {
        // Obtener el token JWT
        String token = authService.login("test", "12345");

        // Datos de entrada: items y monto
        String requestBody = """
            {
                "itemIds": ["MLA719052d9-9655-11ef-bdc6-c03eba70363e", "MLA71905420-9655-11ef-bdc6-c03eba70363e", "MLA719054e6-9655-11ef-bdc6-c03eba70363e"],
                "amount": 5000
            }
        """;

        // Realizar la solicitud POST utilizando MockMvc y pasar el token en los headers
        mockMvc.perform(post("/coupon/")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"itemIds\":[\"MLA71905420-9655-11ef-bdc6-c03eba70363e\"],\"total\":123.42}"));
    }
}

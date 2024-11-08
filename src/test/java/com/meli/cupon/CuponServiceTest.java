package com.meli.cupon;

import com.meli.cupon.model.entity.Item;
import com.meli.cupon.model.response.CuponResponse;
import com.meli.cupon.service.CuponService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CuponServiceTest {

    @Autowired
    private CuponService cuponService;

    @Test
    void testCalculateOptimalItems() {
        // Arrange: Creamos una lista de ítems de prueba
        List<Item> items = new ArrayList<>();
        Item i1 = new Item();
        i1.setId("MLA719052d9-9655-11ef-bdc6-c03eba70363e");
        i1.setPrice(12312.39);
        Item i2 = new Item();
        i2.setId("MLA71905420-9655-11ef-bdc6-c03eba70363e");
        i2.setPrice(123.42);
        Item i3 = new Item();
        i3.setId("MLA719054e6-9655-11ef-bdc6-c03eba70363e");
        i3.setPrice(6698.91);
        items.add(i1);
        items.add(i2);
        items.add(i3);
        double amount = 5000;

        // Act: Ejecutamos el cálculo
        CuponResponse response = cuponService.calculateOptimalItems(items, amount);

        // Assert: Verificamos que la respuesta contenga los ítems correctos
        List<String> expectedItems = Arrays.asList("MLA71905420-9655-11ef-bdc6-c03eba70363e");  // Solo el Item 2 debería ser elegido
        assertEquals(expectedItems, response.getItemIds());
        assertEquals(123.42, response.getTotal(), 0.01);  // Total gastado debe ser 123.42
    }
}

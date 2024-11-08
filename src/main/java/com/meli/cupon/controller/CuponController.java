package com.meli.cupon.controller;

import com.meli.cupon.model.response.CuponRequest;
import com.meli.cupon.model.response.CuponResponse;
import com.meli.cupon.model.entity.Item;
import com.meli.cupon.service.CuponService;
import com.meli.cupon.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Controlador para la gestión de cupones.
 * 
 * Este controlador maneja las solicitudes para aplicar cupones a un conjunto de items. Proporciona 
 * un endpoint para que los usuarios puedan enviar un **request** con los IDs de los items y el monto, 
 * y recibe una respuesta con los items óptimos para aplicar el cupón.
 */
@RestController
@RequestMapping("/coupon")
public class CuponController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private CuponService cuponService;

    /**
     * Endpoint para aplicar un cupón a un conjunto de items.
     * 
     * Este endpoint recibe una solicitud con los IDs de los items y el monto al que se aplica el cupón.
     * Luego, obtiene los detalles de los items desde el servicio de `ItemService` y calcula los items óptimos
     * para el cupón mediante el servicio `CuponService`.
     * 
     * @param request El objeto **CuponRequest** que contiene los IDs de los items y el monto.
     * @return Un objeto **CuponResponse** con la información de los items óptimos a aplicar para el cupón.
     */
    @PostMapping("/")
    public ResponseEntity<CuponResponse> applyCoupon(@RequestBody CuponRequest request) {
        List<String> itemIds = request.getItemIds();
        
        // Obtiene los items con base en los IDs proporcionados
        List<Item> items = itemService.getItemsByIds(itemIds).block();
        
        // Calcula los items óptimos para el cupón
        CuponResponse response = cuponService.calculateOptimalItems(items, request.getAmount());
        return ResponseEntity.ok(response);
    }

}

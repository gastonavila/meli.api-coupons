package com.meli.cupon.service;

import com.meli.cupon.exception.CuponNotFoundException;
import com.meli.cupon.model.response.CuponResponse;
import com.meli.cupon.model.entity.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de calcular la mejor combinación de ítems para aplicar un cupón.
 * 
 * Esta clase se encarga de seleccionar los ítems más baratos dentro de un presupuesto, 
 * garantizando que el total no exceda el monto disponible. Además, devuelve la lista de 
 * ítems seleccionados y el total gastado.
 */
@Service
public class CuponService {
    
    /**
     * Calcula los ítems óptimos que pueden ser comprados dentro de un monto.
     * 
     * Ordena los ítems por precio, seleccionando aquellos cuya suma no exceda el monto disponible.
     * 
     * @param items Lista de ítems disponibles para la compra.
     * @param amount El monto total disponible para gastar.
     * @return Una respuesta con los ítems seleccionados y el total gastado.
     */
    public CuponResponse calculateOptimalItems(List<Item> items, double amount) {
        if (items.isEmpty()) {
            throw new CuponNotFoundException("No se han encontrado ítems para aplicar el cupón.");
        }
        else{
            // Ordena los ítems por precio (de menor a mayor)
        items.sort(Comparator.comparingDouble(Item::getPrice));
        
        List<String> selectedItems = new ArrayList<>();
        for (Item i : items) {
        }
        double totalSpent = 0.0;
        // Recorre los ítems y selecciona aquellos que se puedan pagar sin exceder el monto disponible
        for (Item item : items) {
            if (totalSpent + item.getPrice() <= amount) {
                selectedItems.add(item.getId());
                totalSpent += item.getPrice();
            }
        }
        
        // Crea y devuelve la respuesta con los ítems seleccionados y el total gastado
        CuponResponse response = new CuponResponse();
        response.setItemIds(selectedItems);
        response.setTotal(totalSpent);
        return response;
        }
    }
}

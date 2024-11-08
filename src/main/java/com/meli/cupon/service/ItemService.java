package com.meli.cupon.service;

import com.meli.cupon.exception.ItemNotFoundException;
import com.meli.cupon.model.entity.Item;
import com.meli.cupon.repository.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Servicio encargado de la obtención de ítems a través de una API externa.
 * 
 * Esta clase interactúa con una API externa de ítems (como un sistema de inventario o catálogo) 
 * para obtener información detallada de los ítems utilizando **WebClient**.
 */
@Service
public class ItemService {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    private final String ITEM_API_URL = "https://meli-api-items.wl.r.appspot.com/api/items/";
    
    /**
     * Obtiene los ítems por sus IDs desde una API externa.
     * 
     * Realiza múltiples llamadas a la API para obtener información sobre varios ítems en paralelo. 
     * Utiliza la programación reactiva para manejar las respuestas de forma eficiente.
     * 
     * @param itemIds Lista de IDs de los ítems que se desean obtener.
     * @return Una lista de **Mono<Item>**, representando la respuesta reactiva de cada ítem.
     */
    public Mono<List<Item>> getItemsByIds(List<String> itemIds) {
        List<Mono<Item>> itemMonos = new ArrayList<>();
        
        // Creamos una lista de Mono para las peticiones de cada item
        for (String itemId : itemIds) {
            itemMonos.add(obtenerItemPorId(itemId));  // Hacemos la llamada para cada item
        }

        // Usamos zip para combinar todos los resultados de los Monos de items en una lista
        return Mono.zip(itemMonos, results -> {
            List<Item> items = new ArrayList<>();
            for (Object result : results) {
                items.add((Item) result);  // Convertimos los resultados a objetos Item
            }
            return items;
        });
    }
    
    // Método que realiza una llamada reactiva a la API de Items para obtener un solo item por su ID
    private Mono<Item> obtenerItemPorId(String itemId) {
        return webClientBuilder.baseUrl(ITEM_API_URL)
                .build()
                .get()
                .uri(itemId)  // El item se pasa en la URL como parámetro
                .retrieve()
                .bodyToMono(Item.class)
                .onErrorMap(e -> new ItemNotFoundException("Error al obtener el ítem: " + itemId));  // Convierte la respuesta JSON a un objeto Item
    }
}

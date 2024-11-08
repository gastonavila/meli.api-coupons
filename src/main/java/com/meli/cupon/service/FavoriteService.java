package com.meli.cupon.service;

import com.meli.cupon.model.response.FavoriteResponse;
import com.meli.cupon.repository.FavoriteRepository;
import java.util.List;
import com.meli.cupon.exception.FavoriteNotFoundException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de obtener los ítems más populares basados en las veces que han sido 
 * marcados como favoritos.
 * 
 * Esta clase se encarga de calcular cuáles son los ítems más populares (más frecuentemente 
 * marcados como favoritos) utilizando el repositorio **FavoriteRepository**.
 */
@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    /**
     * Obtiene los ítems más populares basados en los que más han sido marcados como favoritos.
     * 
     * Llama al repositorio para obtener la lista de ítems más favoritos, y limita el resultado
     * a los **5** primeros.
     * 
     * @return Una lista con los 5 ítems más populares, ordenados por la cantidad de favoritos.
     */
    public List<FavoriteResponse> getTopFavorites() {

        List<Object[]> results = favoriteRepository.findTopFavorites();

        if (results == null || results.isEmpty()) {
            throw new FavoriteNotFoundException("No se encontraron favoritos en la base de datos.");
        }
        // Mapea los resultados a objetos de tipo FavoriteResponse
        List<FavoriteResponse> favorites = results.stream().limit(5).map(result -> {
            FavoriteResponse favoriteResponse = new FavoriteResponse();
            favoriteResponse.setId((String) result[0]);
            favoriteResponse.setQuantity(((Number) result[1]).intValue());
            return favoriteResponse;
        }).collect(Collectors.toList());

        return favorites;

    }

}

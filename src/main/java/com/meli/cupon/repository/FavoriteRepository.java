package com.meli.cupon.repository;

import com.meli.cupon.model.Favorite;
import com.meli.cupon.model.FavoriteId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio para gestionar las operaciones relacionadas con los favoritos.
 * 
 * Este repositorio extiende **JpaRepository** y se utiliza para acceder a los datos de la tabla `favorite`.
 * Contiene una consulta personalizada para obtener los ítems más populares basados en la cantidad de veces que 
 * un ítem ha sido marcado como favorito.
 */
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    /**
     * Consulta personalizada para obtener los ítems más populares basados en la cantidad de veces que
     * han sido marcados como favoritos.
     * 
     * Esta consulta devuelve un listado de objetos, donde cada objeto contiene el **ID** del item 
     * y la cantidad de veces que ha sido marcado como favorito. Los resultados son ordenados por la 
     * cantidad de favoritos en orden descendente.
     * 
     * @return Una lista de arrays de objetos donde el primer elemento es el ID del item y el segundo es la cantidad.
     */
    @Query("SELECT f.id.itemId, COUNT(f) AS quantity " +
           "FROM Favorite f " +
           "GROUP BY f.id.itemId " +
           "ORDER BY quantity DESC")
    List<Object[]> findTopFavorites();
}

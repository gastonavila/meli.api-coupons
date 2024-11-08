package com.meli.cupon.controller;

import com.meli.cupon.model.response.FavoriteResponse;
import com.meli.cupon.service.FavoriteService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para obtener estadísticas relacionadas con los favoritos.
 * 
 * Este controlador maneja las solicitudes para obtener las estadísticas de los ítems más populares o favoritos. 
 * Proporciona un endpoint para obtener los ítems más favoriteados.
 */
@RestController
@RequestMapping("/coupon/stats")
public class StatsController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    /**
     * Endpoint para obtener los ítems más populares o favoritos.
     * 
     * Este endpoint obtiene las estadísticas de los ítems más favoriteados.
     * @return Una lista de **FavoriteResponse** que contiene los ítems favoritos más destacados.
     */
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getTopFavorites() {
        List<FavoriteResponse> topFavorites = favoriteService.getTopFavorites();
        return ResponseEntity.ok(topFavorites);
    }
}

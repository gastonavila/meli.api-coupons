package com.meli.cupon.exception;

/**
 * Excepción personalizada lanzada cuando no se encuentra un favorito.
 */
public class FavoriteNotFoundException extends RuntimeException {

    public FavoriteNotFoundException(String message) {
        super(message);
    }
}


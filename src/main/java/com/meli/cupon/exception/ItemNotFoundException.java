package com.meli.cupon.exception;

/**
 * Excepción personalizada lanzada cuando no se encuentra un ítem en la base de datos o en la API externa.
 */
public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }
}


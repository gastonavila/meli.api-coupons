package com.meli.cupon.exception;

/**
 * Excepción personalizada lanzada cuando hay un error con la lógica del cupón.
 */
public class CuponNotFoundException extends RuntimeException {

    public CuponNotFoundException(String message) {
        super(message);
    }
}

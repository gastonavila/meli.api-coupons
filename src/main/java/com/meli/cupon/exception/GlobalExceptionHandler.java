package com.meli.cupon.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones.
 * Esta clase maneja todas las excepciones que se producen en la aplicación.
 * Usa la anotación `@RestControllerAdvice` para manejar excepciones de manera global para 
 * los controladores REST. Se utiliza con el fin de proporcionar una respuesta adecuada según el tipo de
 * excepción que ocurra.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja excepciones de credenciales inválidas.
     * 
     * Cuando ocurre un error debido a credenciales incorrectas (por ejemplo, usuario o 
     * contraseña inválidos durante la autenticación), se captura la excepción 
     * **BadCredentialsException** y se devuelve una respuesta con el código de estado **401 Unauthorized** 
     * y un mensaje claro indicando que las credenciales son inválidas.
     * 
     * @param ex Excepción que se lanza cuando las credenciales proporcionadas no son correctas.
     * @return Una respuesta con un código de estado **401 Unauthorized** y el mensaje de error correspondiente.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nombre de usuario o password inválidos.");
    }
    
    /**
     * Maneja la excepción ItemNotFoundException.
     * 
     * Devuelve un código de estado 404 (Not Found) con un mensaje adecuado.
     * 
     * @param ex La excepción lanzada cuando un ítem no es encontrado.
     * @return Un mensaje con el detalle del error.
     */
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ítem no encontrado: " + ex.getMessage());
    }
    
    /**
     * Maneja la excepción FavoriteNotFoundException.
     * 
     * Devuelve un código de estado 404 (Not Found) con un mensaje adecuado.
     * 
     * @param ex La excepción lanzada cuando no se encuentra un favorito.
     * @return Un mensaje con el detalle del error.
     */
    @ExceptionHandler(FavoriteNotFoundException.class)
    public ResponseEntity<String> handleFavoriteNotFound(FavoriteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorito no encontrado: " + ex.getMessage());
    }
    
    /**
     * Maneja la excepción CuponNotFoundException.
     * 
     * Devuelve un código de estado 400 (Bad Request) con un mensaje adecuado.
     * 
     * @param ex La excepción lanzada cuando hay un error con el cupón.
     * @return Un mensaje con el detalle del error.
     */
    @ExceptionHandler(CuponNotFoundException.class)
    public ResponseEntity<String> handleCuponNotFound(CuponNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error con el cupón: " + ex.getMessage());
    }
    
    /**
     * Maneja excepciones generales.
     * 
     * Devuelve un código de estado 500 (Internal Server Error) con un mensaje general de error.
     * 
     * @param ex La excepción general no manejada.
     * @return Un mensaje con el detalle del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + ex.getMessage());
    }
    
}


package com.meli.cupon.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Entidad que representa un item dentro de la base de datos.
 * 
 * Esta clase mapea la información de un item a la tabla `items` en la base de datos, que incluye 
 * un **ID** y el **precio** del item. La validación asegura que tanto el ID como el precio no 
 * sean nulos.
 */
@Data
@Entity
@Table(name="items")
public class Item implements Serializable{
    
    @Id
    @NotNull(message = "El ID no puede ser nulo.")
    private String id;

    @NotNull(message = "El precio no puede ser nulo.")
    private Double price;

}

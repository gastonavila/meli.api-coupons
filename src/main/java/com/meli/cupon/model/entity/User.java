package com.meli.cupon.model.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa a un usuario en la base de datos.
 * 
 * Esta clase se utiliza para mapear los datos de un usuario en la base de datos, incluyendo su 
 * **ID**, **nombre**, **apellido**, y **edad**. Los datos son almacenados en la tabla `users`.
 * 
 * La validación asegura que el campo **ID** no sea nulo, lo cual es importante para la integridad
 * de la base de datos.
 */
@Data
@Entity
@Table(name="users")
public class User implements Serializable{
    
    @Id
    @NotNull(message = "El ID no puede ser nulo.")
    private Long id;

    private String nombre;
    private String apellido;
    private int edad;

}
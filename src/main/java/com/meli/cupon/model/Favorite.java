package com.meli.cupon.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name = "favorites")
public class Favorite implements Serializable{
    
    @EmbeddedId
    private FavoriteId id;

}
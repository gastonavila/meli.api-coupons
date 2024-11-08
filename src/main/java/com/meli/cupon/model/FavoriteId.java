package com.meli.cupon.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class FavoriteId implements Serializable{
    
    private Long userId;

    private String itemId;

}
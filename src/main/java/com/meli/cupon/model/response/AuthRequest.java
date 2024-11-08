package com.meli.cupon.model.response;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;

}

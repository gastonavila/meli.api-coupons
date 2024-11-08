package com.meli.cupon.model.response;

import java.util.List;
import lombok.Data;

@Data
public class CuponRequest {
    private List<String> itemIds;
    private double amount;
}

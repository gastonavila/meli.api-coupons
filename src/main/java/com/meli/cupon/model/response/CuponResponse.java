package com.meli.cupon.model.response;

import java.util.List;
import lombok.Data;

@Data
public class CuponResponse {
    private List<String> itemIds;
    private double total;
}

package com.bootcamp.be_java_hisp_w29_g3.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PromoProductDto {
    private Integer userId;
    private String userName;
    private Long promoProductsCount;
}

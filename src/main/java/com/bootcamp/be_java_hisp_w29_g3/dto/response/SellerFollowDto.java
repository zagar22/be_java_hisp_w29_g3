package com.bootcamp.be_java_hisp_w29_g3.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerFollowDto {
    private Integer userId;

    @NotEmpty(message = "El nombre de usuario no puede estar vació")
    @Size(max = 15, message = "El nombre de usuario no púede tener mas de 15 caracteres")
    private String userName;
}

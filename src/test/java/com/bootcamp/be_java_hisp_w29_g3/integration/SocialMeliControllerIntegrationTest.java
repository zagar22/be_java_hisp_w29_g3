package com.bootcamp.be_java_hisp_w29_g3.integration;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.PromoProductDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialMeliControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){

    }

    @Test
    void getPromoProductsTest() throws Exception {
        Integer userId = 1;
        PromoProductDto promoProductDto = new PromoProductDto(userId,"Vendedor A",2L);
        Long promoProductCount = 5L;
        mockMvc.perform(get("/products/promo-post/count")
                        .param("user_id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.user_name").value("Vendedor A"))
                .andExpect(jsonPath("$.promo_products_count").value(1L));
    }

}

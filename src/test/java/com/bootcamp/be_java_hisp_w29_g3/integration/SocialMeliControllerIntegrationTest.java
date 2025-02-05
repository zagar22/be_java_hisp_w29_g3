package com.bootcamp.be_java_hisp_w29_g3.integration;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.PromoProductDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SocialMeliControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("US-0011")
    void getPromoProductsTest() throws Exception {
        Integer userId = 1;
        mockMvc.perform(get("/products/promo-post/count")
                        .param("user_id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.user_name").value("Vendedor A"))
                .andExpect(jsonPath("$.promo_products_count").value(1L));
    }


    @DisplayName("US-0002 - Happy path")
    @Test
    void getSellerFollowerCountTest() throws Exception {
        Integer sellerId = 1;
        String userName = "Vendedor A";
        Integer followersCount = 2;
        mockMvc.perform(get("/users/{sellerId}/followers/count", sellerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(sellerId))
                .andExpect(jsonPath("$.user_name").value(userName))
                .andExpect(jsonPath("$.followers_count").value(followersCount));
    }
    @DisplayName("US-0002 - Seller not found")
    @Test
    void getSellerFollowerCountTestSadPath() throws Exception {
        Integer sellerId = 3;
        mockMvc.perform(get("/users/{sellerId}/followers/count", sellerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("US-0001")
    void followSellerTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}",2, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Vendedor seguido correctamente"));
    }

    @Test
    @DisplayName("US-0007")
    void unfollowSellerTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}",1, 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El usuario ya no sigue al vendedor"));
    }


}

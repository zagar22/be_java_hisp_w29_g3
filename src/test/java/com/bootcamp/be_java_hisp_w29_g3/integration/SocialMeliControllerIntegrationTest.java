package com.bootcamp.be_java_hisp_w29_g3.integration;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.PromoProductDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import com.bootcamp.be_java_hisp_w29_g3.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
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
    @DisplayName("US-0011 (Productos con descuento)")
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

    @Test
    @DisplayName("US-0011 (Productos con descuento) no encuentra el id")
    void getPromoProductsBadTest() throws Exception {
        Integer userId = 9887;
        mockMvc.perform(get("/products/promo-post/count")
                        .param("user_id", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
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

    @Test
    @DisplayName("UH-4: DESC (lista de seguidores del comprador)")
    void getSellersFollowedByBuyerTestDESC() throws Exception {
        Integer userId = 1;
        String order = "name_desc";

        mockMvc.perform(get("/users/{userId}/followed/list", userId)
                        .param("order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.user_name").value("Comprador X"))
                .andExpect(jsonPath("$.followed[0].user_id").value(2))
                .andExpect(jsonPath("$.followed[0].user_name").value("Vendedor B"))
                .andExpect(jsonPath("$.followed[1].user_id").value(1))
                .andExpect(jsonPath("$.followed[1].user_name").value("Vendedor A"));
    }

    @Test
    @DisplayName("UH-4: ASC")
    void getSellersFollowedByBuyerTestASC() throws Exception {
        Integer userId = 1;
        String order = "name_asc";

        mockMvc.perform(get("/users/{userId}/followed/list", userId)
                        .param("order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userId))
                .andExpect(jsonPath("$.user_name").value("Comprador X"))
                .andExpect(jsonPath("$.followed[0].user_id").value(1))
                .andExpect(jsonPath("$.followed[0].user_name").value("Vendedor A"))
                .andExpect(jsonPath("$.followed[1].user_id").value(2))
                .andExpect(jsonPath("$.followed[1].user_name").value("Vendedor B"));
    }

    @Test
    @DisplayName("UH-3: Obtener lista de seguidores de un vendedor en orden ASC")
    void getSellerFollowersTestASC() throws Exception {
        Integer sellerId = 1;
        String order = "name_asc";

        mockMvc.perform(get("/sellers/{sellerId}/followers", sellerId)
                        .param("order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.user_id").value(sellerId))
                        .andExpect(jsonPath("$.user_name").value("Vendedor A"))
                        .andExpect(jsonPath("$.followers[0].user_id").value(2))
                        .andExpect(jsonPath("$.followers[0].user_name").value("Comprador B"))
                        .andExpect(jsonPath("$.followers[1].user_id").value(3))
                        .andExpect(jsonPath("$.followers[1].user_name").value("Comprador C"));
    }

    @Test
    @DisplayName("UH-3: Obtener lista de seguidores de un vendedor en orden DESC")
    void getSellerFollowersTestDESC() throws Exception {
        Integer sellerId = 1;
        String order = "name_desc";

        mockMvc.perform(get("/sellers/{sellerId}/followers", sellerId)
                    .param("order", order)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.user_id").value(sellerId))
                    .andExpect(jsonPath("$.user_name").value("Vendedor A"))
                    .andExpect(jsonPath("$.followers[0].user_id").value(3))
                    .andExpect(jsonPath("$.followers[0].user_name").value("Comprador C"))
                    .andExpect(jsonPath("$.followers[1].user_id").value(2))
                    .andExpect(jsonPath("$.followers[1].user_name").value("Comprador B"));
    }

    @Test
    @DisplayName("UH-3: Vendedor sin seguidores")
    void getSellerFollowersNoFollowersTest() throws Exception {
        Integer sellerId = 5; // Vendedor sin seguidores

        mockMvc.perform(get("/sellers/{sellerId}/followers", sellerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(sellerId))
                .andExpect(jsonPath("$.user_name").value("Vendedor X"))
                .andExpect(jsonPath("$.followers").isEmpty());
    }

    @Test
    @DisplayName("UH-3: Vendedor no encontrado")
    void getSellerFollowersNotFoundTest() throws Exception {
        Integer sellerId = 999; // id no existe

        mockMvc.perform(get("/sellers/{sellerId}/followers", sellerId)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isNotFound());
    }

}

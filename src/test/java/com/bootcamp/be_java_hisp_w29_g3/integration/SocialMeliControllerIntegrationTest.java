package com.bootcamp.be_java_hisp_w29_g3.integration;

import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.ProductRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocialMeliControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;


    @Test
    @Order(1)
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
    @Order(2)
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
    @DisplayName("US-0001 - Happy Path")
    void followSellerTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}",2, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Vendedor seguido correctamente"));
    }

    @Test
    @DisplayName("US-0001 - NotFound Exception when Buyer not exist")
    void unfollowSellerNotFoundWhenBuyerNotExistTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}",30, 2))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No existe el usuario"));
    }

    @Test
    @DisplayName("US-0007 - Happy Path")
    void unfollowSellerTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}",1, 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("El usuario ya no sigue al vendedor"));
    }

    @Test
    @DisplayName("US-0007 - NotFound Exception when Seller not exist")
    void unfollowSellerNotFoundWhenSellerNotExistTest() throws Exception{
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}",1, 50))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No existe el vendedor"));
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
        String order = "asc";

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
    @DisplayName("US-0005 - Crear un post")
    void createPostTest() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestDto postRequestDto = new PostRequestDto(
                1,
                LocalDate.of(2025, 2, 5), // date
                new ProductRequestDto(
                        1,
                        "Producto A",
                        "Electrónico",
                        "Marca A",
                        "Rojo",
                        "Notas sobre el producto"
                ),
                10,
                5000.0,
                false,
                0.0
        );

        String jsonRequest = objectMapper.writeValueAsString(postRequestDto);
        mockMvc.perform(post("/products/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hasProm").doesNotExist())
                .andExpect(jsonPath("$.discount").doesNotExist());
    }

    @Test
    @DisplayName("US-0005 - Crear un post con datos inválidos (precio mayor que el máximo permitido)")
    void createPostTest_InvalidPrice() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        PostRequestDto postRequestDto = new PostRequestDto(
                1,
                LocalDate.of(2025, 2, 5),
                new ProductRequestDto(
                        1,
                        "Producto A",
                        "Electrónico",
                        "Marca A",
                        "Rojo",
                        "Notas sobre el producto"
                ),
                10,
                15000000.0,
                false,
                null
        );

        String jsonRequest = objectMapper.writeValueAsString(postRequestDto);

        mockMvc.perform(post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.price").value("El precio máximo permitido es 10,000,000."));
    }

    @Test
    @DisplayName("US-0010 - crear un post con promocion")
    void createPromoPostTest() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestDto postRequestDto = new PostRequestDto(
                1,
                LocalDate.of(2025, 2, 5), // date
                new ProductRequestDto(
                        1,
                        "Producto A",
                        "Electrónico",
                        "Marca A",
                        "Rojo",
                        "Notas sobre el producto"
                ),
                10,
                5000.0,
                true,
                20.0
        );

        String jsonRequest = objectMapper.writeValueAsString(postRequestDto);
        mockMvc.perform(post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.has_prom").value(true))
                .andExpect(jsonPath("$.discount").value(20.0));
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
    @Test
    @DisplayName("US-0006 - Order default")
    void getPostsByUserIdInLastTwoWeeksTest() throws Exception{
        mockMvc.perform(get("/products/followed/{userId}/list",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.posts[0].user_id").value(1))
                .andExpect(jsonPath("$.posts[0].post_id").value(1))
                .andExpect(jsonPath("$.posts[1].user_id").value(1))
                .andExpect(jsonPath("$.posts[1].post_id").value(2));
    }

    @Test
    @DisplayName("US-0006 - Seller NotFound")
    void getPostsByUserIdInLastTwoWeeksSellerNotFoundTest() throws Exception{
        mockMvc.perform(get("/products/followed/{userId}/list",100))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No existe el usuario"));
    }

    @Test
    @DisplayName("US-0009 - Order desc")
    void getPostsByUserIdInLastTwoWeeksDescTest() throws Exception{
        mockMvc.perform(get("/products/followed/{userId}/list?order=date_desc",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.posts[0].user_id").value(1))
                .andExpect(jsonPath("$.posts[0].post_id").value(1))
                .andExpect(jsonPath("$.posts[1].user_id").value(1))
                .andExpect(jsonPath("$.posts[1].post_id").value(2));
    }

    @Test
    @DisplayName("US-0009 - Order asc")
    void getPostsByUserIdInLastTwoWeeksAscTest() throws Exception{
        mockMvc.perform(get("/products/followed/{userId}/list?order=date_asc",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.posts[0].user_id").value(1))
                .andExpect(jsonPath("$.posts[0].post_id").value(2))
                .andExpect(jsonPath("$.posts[1].user_id").value(1))
                .andExpect(jsonPath("$.posts[1].post_id").value(1));
    }
}

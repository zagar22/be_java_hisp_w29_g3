package com.bootcamp.be_java_hisp_w29_g3.util;

import com.bootcamp.be_java_hisp_w29_g3.dto.ProductDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.BasicPostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FullPostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostByUserDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.bootcamp.be_java_hisp_w29_g3.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class PostMapperUtil {


    public static PostResponseDto mapToFullPostResponseDto(Post post, ObjectMapper objectMapper) {
        FullPostResponseDto response = objectMapper.convertValue(post, FullPostResponseDto.class);
        response.setPost_id(post.getId());
        return response;
    }


    public static PostResponseDto mapToBasicPostResponseDto(Post post, ObjectMapper objectMapper) {
        BasicPostResponseDto response = objectMapper.convertValue(post, BasicPostResponseDto.class);
        response.setPost_id(post.getId());
        return response;
    }

    public static List<PostByUserDto> mapToPostByUserResponseDto(List<Post> posts, Integer userId) {
        List<PostByUserDto> dtos = new ArrayList<>();
        posts.forEach(post -> {
            dtos.add(buildPostByUserDto(post, userId));
        });
        return dtos;
    }

    private static PostByUserDto buildPostByUserDto(Post post, Integer userId){
        PostByUserDto dto = new PostByUserDto();
        dto.setUserId(userId);
        dto.setPostId(post.getId());
        dto.setProduct(buildProductDto(post.getProduct()));
        dto.setDate(post.getDate());
        dto.setPrice(post.getPrice());
        dto.setCategory(post.getCategory());
        return dto;
    }

    private static ProductDto buildProductDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setType(product.getType());
        dto.setNotes(product.getNotes());
        dto.setColor(product.getColor());
        dto.setBrand(product.getBrand());
        return dto;
    }
}


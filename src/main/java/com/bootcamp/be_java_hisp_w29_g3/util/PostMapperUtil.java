package com.bootcamp.be_java_hisp_w29_g3.util;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostByUserDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PostMapperUtil {

    public static PostResponseDto mapToPostResponseDto(Post post, ObjectMapper objectMapper) {

        PostResponseDto response = objectMapper.convertValue(post, PostResponseDto.class);

        response.setPost_id(post.getId());

        return response;
    }

    public static PostByUserDto mapToPostByUserResponseDto(Post post, Integer userId, ObjectMapper objectMapper) {
        PostByUserDto response = objectMapper.convertValue(post, PostByUserDto.class);
        response.setPostId(post.getId());
        response.setUserId(userId);
        return response;
    }
}


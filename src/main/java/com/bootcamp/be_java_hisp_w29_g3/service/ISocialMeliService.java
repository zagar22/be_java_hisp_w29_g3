package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import org.springframework.web.bind.annotation.PathVariable;

import com.bootcamp.be_java_hisp_w29_g3.dto.PostDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostResponseDto;

public interface ISocialMeliService {
    FollowDto followSeller(int userId, int userIdToFollow);
    UnfollowDto unfollowSeller(int userId, int userIdToUnfollow);

    PostResponseDto createPost(PostRequestDto post);
    FollowerCountDTO calculateSellerFollowerCount(@PathVariable Integer sellerId);
}

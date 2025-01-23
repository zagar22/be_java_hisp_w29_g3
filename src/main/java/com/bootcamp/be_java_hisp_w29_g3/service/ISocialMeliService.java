package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.BuyerFollowedSellersDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ISocialMeliService {
    FollowDto followSeller(int userId, int userIdToFollow);
    UnfollowDto unfollowSeller(int userId, int userIdToUnfollow);
    BuyerFollowedSellersDto getUserFollowSellers(Integer buyerId);
    PromoProductDto getPromoProducts(Integer userId);
    UserFollowersDTO getFollowers(int sellerId, String order);
    PostResponseDto createPost(PostRequestDto post);
    FollowerCountDTO calculateSellerFollowerCount(@PathVariable Integer sellerId);
    PostsByUserResponseDto searchPostsById(Integer userId, String order);
    List<PostDto> filterPostsByDiscountRange(Integer initialValue, Integer finalValue);
}

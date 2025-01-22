package com.bootcamp.be_java_hisp_w29_g3.repository;

import com.bootcamp.be_java_hisp_w29_g3.entity.Buyer;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;

import java.util.List;
import java.util.Map;

public interface IUserRepository {
    Map<Integer, Seller> getAll();
    List<Seller> getSellersFollowedByBuyer(int buyerId);
    Long countPromotionalProductsBySeller(int sellerId);
    void followSeller(int userId, int userIdToFollow);
    void unfollowSeller(int userId, int userIdToUnfollow);
    boolean existsSellerById(int userIdToFollow);
    boolean existsBuyerById(int userId);
    boolean buyerAlreadyFollowsSeller(int userId, int userIdToFollow);
    Buyer getBuyerById(Integer userId);
    Post addPostToSeller(Integer userId, Post newPost);
    List<Buyer> getBuyersFollowingSeller(Integer sellerId);
    Seller getSellerById(Integer id);
    List<Seller> getFollowers(int sellerId, String order);
}

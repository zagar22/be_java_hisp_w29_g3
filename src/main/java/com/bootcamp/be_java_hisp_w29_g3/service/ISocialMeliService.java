package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface ISocialMeliService {
    FollowDto followSeller(int userId, int userIdToFollow);
    UnfollowDto unfollowSeller(int userId, int userIdToUnfollow);
    FollowerCountDTO calculateSellerFollowerCount(@PathVariable Integer sellerId);
}

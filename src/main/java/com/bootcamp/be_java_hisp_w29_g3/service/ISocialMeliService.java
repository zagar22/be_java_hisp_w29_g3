package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;

public interface ISocialMeliService {
    FollowDto followSeller(int userId, int userIdToFollow);
}

package com.bootcamp.be_java_hisp_w29_g3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserFollowersDTO {
    private int userId;
    private List<UserDTO> followers;
}


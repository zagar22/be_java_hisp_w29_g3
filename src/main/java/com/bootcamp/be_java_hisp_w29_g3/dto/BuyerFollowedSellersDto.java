package com.bootcamp.be_java_hisp_w29_g3.dto;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.SellerFollowDto;

import java.util.List;

public class BuyerFollowedSellersDto {
    private Integer user_id;
    private String user_name;
    private List<SellerFollowDto> followed;

    public BuyerFollowedSellersDto(Integer user_id, String user_name, List<SellerFollowDto> followed) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.followed = followed;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public List<SellerFollowDto> getFollowed() {
        return followed;
    }

    public void setFollowed(List<SellerFollowDto> followed) {
        this.followed = followed;
    }
}

package com.bootcamp.be_java_hisp_w29_g3.dto.response;

public class SellerFollowDto {
    private Integer user_id;
    private String user_name;

    public SellerFollowDto(Integer user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
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
}

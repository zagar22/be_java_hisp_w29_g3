package com.bootcamp.be_java_hisp_w29_g3.dto;

public class FollowDto {
    private String message;

    public FollowDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

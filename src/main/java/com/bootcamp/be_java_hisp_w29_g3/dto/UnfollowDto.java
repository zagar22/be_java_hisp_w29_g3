package com.bootcamp.be_java_hisp_w29_g3.dto;

public class UnfollowDto {
    private String message;

    public UnfollowDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

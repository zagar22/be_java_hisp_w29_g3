package com.bootcamp.be_java_hisp_w29_g3.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}

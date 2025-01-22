package com.bootcamp.be_java_hisp_w29_g3.dto.response;

public class SellerDto {
    private Integer id;
    private String name;

    public SellerDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

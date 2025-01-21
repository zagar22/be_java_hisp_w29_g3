package com.bootcamp.be_java_hisp_w29_g3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class Buyer extends User{
    private List<Seller> sellers;

    public Buyer(Integer id, String name, List<Seller> sellers) {
        super(id, name);
        this.sellers = sellers;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }
}

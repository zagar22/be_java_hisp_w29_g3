package com.bootcamp.be_java_hisp_w29_g3.repository;

import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;

import java.util.Map;

public interface IUserRepository {
    Map<Integer, Seller> getAll();
}

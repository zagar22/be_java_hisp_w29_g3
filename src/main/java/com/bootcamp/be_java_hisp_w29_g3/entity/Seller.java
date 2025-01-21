package com.bootcamp.be_java_hisp_w29_g3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class Seller extends User {
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public Seller(Integer id,String name, List<Post> posts) {
        super(id,name);
        this.posts = posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}

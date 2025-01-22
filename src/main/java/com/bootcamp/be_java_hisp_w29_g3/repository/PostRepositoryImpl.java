package com.bootcamp.be_java_hisp_w29_g3.repository;

import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements IPostRepository {
    private List<Post> listOfPosts = new ArrayList<>();

    @Override
    public List<Post> findAll() {
        return listOfPosts;
    }

    @Override
    public void addPost(Post post) {
        listOfPosts.add(post);
    }
}

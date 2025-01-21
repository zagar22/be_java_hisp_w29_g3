package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class SocialMeliServiceImpl  implements ISocialMeliService{
    private IUserRepository userRepository;
    private IPostRepository postRepository;
    private ObjectMapper mapper;

    public SocialMeliServiceImpl(){
    }




}

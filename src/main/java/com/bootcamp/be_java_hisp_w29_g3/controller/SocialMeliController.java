package com.bootcamp.be_java_hisp_w29_g3.controller;

import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMeliController {
    private IUserRepository userRepository;
    public SocialMeliController(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>( userRepository.getAll(), HttpStatus.OK);
    }
}

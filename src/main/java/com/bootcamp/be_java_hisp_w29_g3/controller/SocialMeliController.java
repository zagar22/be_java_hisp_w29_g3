package com.bootcamp.be_java_hisp_w29_g3.controller;

import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g3.service.ISocialMeliService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMeliController {
    private IUserRepository userRepository;
    private ISocialMeliService socialMeliService;

    public SocialMeliController(IUserRepository userRepository, ISocialMeliService socialMeliService) {
        this.userRepository = userRepository;
        this.socialMeliService = socialMeliService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>( userRepository.getAll(), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(@PathVariable int userId, @PathVariable int userIdToFollow){
        return new ResponseEntity<>(socialMeliService.followSeller(userId,userIdToFollow),HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unFollowSeller(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        return new ResponseEntity<>(socialMeliService.unfollowSeller(userId,userIdToUnfollow),HttpStatus.OK);
    }
}

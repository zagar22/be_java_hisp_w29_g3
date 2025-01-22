package com.bootcamp.be_java_hisp_w29_g3.controller;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g3.service.ISocialMeliService;

import com.bootcamp.be_java_hisp_w29_g3.service.ISocialMeliService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialMeliController {
    private IUserRepository userRepository;
    private final ISocialMeliService socialMeliService;


    public SocialMeliController(ISocialMeliService socialMeliService, IUserRepository userRepository){
        this.userRepository = userRepository;
        this.socialMeliService = socialMeliService;
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>( userRepository.getAll(), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<FollowDto> followSeller(@PathVariable int userId, @PathVariable int userIdToFollow){
        return new ResponseEntity<>(socialMeliService.followSeller(userId,userIdToFollow),HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<UnfollowDto> unFollowSeller(@PathVariable int userId, @PathVariable int userIdToUnfollow){
        return new ResponseEntity<>(socialMeliService.unfollowSeller(userId,userIdToUnfollow),HttpStatus.OK);
    }

//    punto 2
    @GetMapping("/users/{sellerId}/followers/count")
    public ResponseEntity<FollowerCountDTO> getSellerFollowerCount(@PathVariable Integer sellerId){
        return new ResponseEntity<>(socialMeliService.calculateSellerFollowerCount(sellerId),HttpStatus.OK);
    }

    @PostMapping("/products/post")
    public ResponseEntity<PostResponseDto> postPost(@RequestBody PostRequestDto post) {
        return new ResponseEntity<>(socialMeliService.createPost(post), HttpStatus.OK);
    }
}

package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UserDTO;
import com.bootcamp.be_java_hisp_w29_g3.dto.UserFollowersDTO;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.bootcamp.be_java_hisp_w29_g3.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g3.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g3.util.JacksonUtil;
import com.bootcamp.be_java_hisp_w29_g3.util.PostMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class SocialMeliServiceImpl implements ISocialMeliService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ObjectMapper mapper = JacksonUtil.createObjectMapper();


    @Override
    public FollowDto followSeller(int userId, int userIdToFollow) {
        //Verifico existencia de vendedor
        if(userRepository.existsSellerById(userIdToFollow)){
            //Verifico existencia de comprador
            if(userRepository.existsBuyerById(userId)){
                //Verifico si ya se siguen
                if(!userRepository.buyerAlreadyFollowsSeller(userId,userIdToFollow)){
                    userRepository.followSeller(userId,userIdToFollow);
                    return new FollowDto("Vendedor seguido correctamente");
                }else{
                    throw new BadRequestException("El usuario ya sigue al vendedor");
                }

            }else{
                throw new BadRequestException("No existe el usuario");
            }

        }else{
            throw new BadRequestException("No existe el vendedor");
        }
    }

    @Override
    public UnfollowDto unfollowSeller(int userId, int userIdToUnfollow) {
        //Verifico existencia de vendedor
        if(userRepository.existsSellerById(userIdToUnfollow)){
            //Verifico existencia de comprador
            if(userRepository.existsBuyerById(userId)){
                //Verifico si ya se siguen
                if(userRepository.buyerAlreadyFollowsSeller(userId,userIdToUnfollow)){
                    userRepository.unfollowSeller(userId,userIdToUnfollow);
                    return new UnfollowDto("El usuario ya no sigue al vendedor");
                }else{
                    throw new BadRequestException("El usuario no sigue al vendedor");
                }

            }else{
                throw new BadRequestException("No existe el usuario");
            }

        }else{
            throw new BadRequestException("No existe el vendedor");
        }
    }

    @Override
    public FollowerCountDTO calculateSellerFollowerCount(Integer sellerId) {
        if (! userRepository.existsSellerById(sellerId)){
            throw new BadRequestException("No existe el vendedor");
        }
        return new FollowerCountDTO(sellerId,
                userRepository.getSellerById(sellerId).getName(),
                userRepository.getBuyersFollowingSeller(sellerId).stream().count());
    }

    @Override
    public PostResponseDto createPost(PostRequestDto post) {
        Integer postNewId = postRepository.findAll().size() + 1;
        Integer userId = post.getUserId();
        Post newPost = mapper.convertValue(post, Post.class);
        newPost.setId(postNewId);
        Post createdPost = userRepository.addPostToSeller(userId, newPost);
        postRepository.addPost(newPost);
        return PostMapperUtil.mapToPostResponseDto(createdPost, mapper);
    }
    public UserFollowersDTO getFollowers(int sellerId, String order) {
        if (!userRepository.existsSellerById(sellerId)) {
            throw new IllegalArgumentException("El vendedor con ID " + sellerId + " no existe.");
        }

        if (!order.isEmpty() && !order.equalsIgnoreCase("name_asc") && !order.equalsIgnoreCase("name_desc")) {
            throw new IllegalArgumentException("El parámetro 'order' debe ser 'name_asc', 'name_desc' o estar vacío.");
        }

        List<UserDTO> followers = userRepository.getFollowers(sellerId, order)
                                                .stream()
                                                .map(seller -> new UserDTO(seller.getId(), seller.getName()))
                                                .collect(Collectors.toList());

        return new UserFollowersDTO(sellerId, followers);
    }
}

package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g3.exception.BadRequestException;
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

    public SocialMeliServiceImpl(IUserRepository userRepository, IPostRepository postRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

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

}

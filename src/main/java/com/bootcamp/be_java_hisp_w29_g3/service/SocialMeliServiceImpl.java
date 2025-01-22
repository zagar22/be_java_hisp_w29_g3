package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.*;
import com.bootcamp.be_java_hisp_w29_g3.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.*;
import com.bootcamp.be_java_hisp_w29_g3.entity.Buyer;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import com.bootcamp.be_java_hisp_w29_g3.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g3.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g3.repository.IPostRepository;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import com.bootcamp.be_java_hisp_w29_g3.util.JacksonUtil;
import com.bootcamp.be_java_hisp_w29_g3.util.PostMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@RequiredArgsConstructor
public class SocialMeliServiceImpl implements ISocialMeliService {
    private final IUserRepository userRepository;
    private final IPostRepository postRepository;
    private final ObjectMapper mapper = JacksonUtil.createObjectMapper();

    private static final String ORDER_DATE_ASC = "date_asc";

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
    public BuyerFollowedSellersDto getUserFollowSellers(Integer buyerId) {
        Buyer buyer = userRepository.getBuyerById(buyerId);
        if (buyer == null){
            throw new NotFoundException("No existe el usuario");
        }
        List<Seller> sellers = userRepository.getSellersFollowedByBuyer(buyerId);
        if (sellers.isEmpty()){
            throw new NotFoundException("El usuario no sigue vendedores");
        }
        List<SellerFollowDto> followedSellers = sellers.stream()
                .map(seller -> new SellerFollowDto(seller.getId(),seller.getName()))
                .collect(Collectors.toList());

        return new BuyerFollowedSellersDto(buyer.getId(),buyer.getName(),followedSellers);
    }

    @Override
    public PromoProductDto getPromoProducts(Integer userId) {
        Seller seller = userRepository.getSellerById(userId);

        if (seller == null){
            throw new NotFoundException("El vendedor no existe");
        }

        Long promoProducts = userRepository.countPromotionalProductsBySeller(userId);
        return new PromoProductDto(seller.getId(),seller.getName(),promoProducts);
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

    @Override
    public PostsByUserResponseDto searchPostsById(Integer userId, String order) {
        List<Seller> sellers = userRepository.getSellersFollowedByBuyer(userId);
        if(sellers.isEmpty())
            throw new NotFoundException("El usuario no sigue a ningún vendedor");

        LocalDate limitDate = LocalDate.now().minusWeeks(2);

        List<Post> posts = sellers.stream()
                .flatMap(seller -> seller.getPosts().stream())
                .filter(seller -> seller.getDate().isAfter(limitDate))
                .sorted(getPostDateComparator(order))
                .toList();

        if(posts.isEmpty())
            throw new NotFoundException("No hay posts para mostrar");

        List<PostByUserDto> postsDto = PostMapperUtil.mapToPostByUserResponseDto(posts, userId);
        return new PostsByUserResponseDto(userId, postsDto);
    }

    private Comparator<Post> getPostDateComparator(String order){
        if(order == null || ORDER_DATE_ASC.equals(order))
            return Comparator.comparing(Post::getDate);

        return Comparator.comparing(Post::getDate).reversed();
    }
}

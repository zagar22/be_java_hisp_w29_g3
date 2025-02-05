package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowDto;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g3.dto.response.UnfollowDto;
import com.bootcamp.be_java_hisp_w29_g3.entity.Buyer;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import com.bootcamp.be_java_hisp_w29_g3.exception.BadRequestException;
import com.bootcamp.be_java_hisp_w29_g3.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SocialMeliServiceImplTest {

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    SocialMeliServiceImpl socialMeliService;

    @Test
    @DisplayName("US-0001 - Happy Path")
    void followSellerOkTest(){
        //Arrange
        Integer userId = 2;
        Integer userIdToFollow = 1;
        FollowDto expected = new FollowDto("Vendedor seguido correctamente");
        when(userRepository.existsBuyerById(userId)).thenReturn(true);
        when(userRepository.existsSellerById(userIdToFollow)).thenReturn(true);
        when(userRepository.buyerAlreadyFollowsSeller(userId,userIdToFollow)).thenReturn(false);

        //Act
        FollowDto actual = socialMeliService.followSeller(userId,userIdToFollow);

        //Assert
        verify(userRepository).followSeller(userId,userIdToFollow);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("US-0001 - NotFound Exception when Seller not exist")
    void followSellerThrowNotFoundExceptionWhenSellerDoesNotExist(){
        //Arrange
        Integer userId = 2;
        Integer userIdToFollow = 10;
        when(userRepository.existsSellerById(userIdToFollow)).thenReturn(false);

        //Assert + Act
        assertThrows(NotFoundException.class,()-> socialMeliService.followSeller(userId,userIdToFollow));
    }

    @Test
    @DisplayName("US-0001 - NotFound Exception when Buyer not exist")
    void followSellerThrowNotFoundExceptionWhenBuyerDoesNotExist(){
        //Arrange
        Integer userId = 2;
        Integer userIdToFollow = 1;
        when(userRepository.existsSellerById(userIdToFollow)).thenReturn(true); //Necesito que pase este, asi va a la siguiente linea
        when(userRepository.existsBuyerById(userId)).thenReturn(false);

        //Assert + Act
        assertThrows(NotFoundException.class,()-> socialMeliService.followSeller(userId,userIdToFollow));
    }

    @Test
    @DisplayName("US-0001 - NotFound Exception when Buyer already follow Seller")
    void followSellerThrowBadRequestExceptionWhenBuyerAlreadyFollowsSeller(){
        //Arrange
        Integer userId = 2;
        Integer userIdToFollow = 1;
        when(userRepository.existsSellerById(userIdToFollow)).thenReturn(true); //Necesito que pase este, asi va a la siguiente linea
        when(userRepository.existsBuyerById(userId)).thenReturn(true);
        when(userRepository.buyerAlreadyFollowsSeller(userId,userIdToFollow)).thenReturn(true);

        //Assert + Act
        assertThrows(BadRequestException.class,()-> socialMeliService.followSeller(userId,userIdToFollow));
    }

    @Test
    @DisplayName("US-0007 - Happy Path")
    void unfollowSellerOkTest(){
        //Arrange
        Integer userId = 2;
        Integer userIdToUnFollow = 1;
        UnfollowDto expected = new  UnfollowDto("El usuario ya no sigue al vendedor");
        when(userRepository.existsBuyerById(userId)).thenReturn(true);
        when(userRepository.existsSellerById(userIdToUnFollow)).thenReturn(true);
        when(userRepository.buyerAlreadyFollowsSeller(userId,userIdToUnFollow)).thenReturn(true);

        //Act
        UnfollowDto actual = socialMeliService.unfollowSeller(userId,userIdToUnFollow);

        //Assert
        verify(userRepository).unfollowSeller(userId,userIdToUnFollow);
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("US-0007 - NotFound Exception when Seller not exist")
    void unfollowSellerThrowNotFoundExceptionWhenSellerDoesNotExist(){
        //Arrange
        Integer userId = 2;
        Integer userIdToUnFollow = 10;
        when(userRepository.existsSellerById(userIdToUnFollow)).thenReturn(false);

        //Assert + Act
        assertThrows(NotFoundException.class,()-> socialMeliService.unfollowSeller(userId,userIdToUnFollow));
    }

    @Test
    @DisplayName("US-0007 - NotFound Exception when Buyer not exist")
    void unfollowSellerThrowNotFoundExceptionWhenBuyerDoesNotExist(){
        //Arrange
        Integer userId = 2;
        Integer userIdToUnFollow = 10;
        when(userRepository.existsSellerById(userIdToUnFollow)).thenReturn(true); //Necesito que pase este, asi va a la siguiente linea
        when(userRepository.existsBuyerById(userId)).thenReturn(false);

        //Assert + Act
        assertThrows(NotFoundException.class,()-> socialMeliService.followSeller(userId,userIdToUnFollow));
    }

    @Test
    @DisplayName("US-0007 - NotFound Exception when Buyer already follow Seller")
    void unfollowSellerThrowBadRequestExceptionWhenBuyerAlreadyFollowsSeller(){
        //Arrange
        Integer userId = 2;
        Integer userIdToUnFollow = 10;
        when(userRepository.existsSellerById(userIdToUnFollow)).thenReturn(true); //Necesito que pase este, asi va a la siguiente linea
        when(userRepository.existsBuyerById(userId)).thenReturn(true);
        when(userRepository.buyerAlreadyFollowsSeller(userId,userIdToUnFollow)).thenReturn(false);

        //Assert + Act
        assertThrows(BadRequestException.class,()-> socialMeliService.unfollowSeller(userId,userIdToUnFollow));
    }

    @DisplayName("US-0002 - Happy path with many followers (T-0007)")
    @Test
    void calculateSellerFollowerCount(){
        //arrange
        Integer sellerId = 1;
        String sellerName = "Juan";
        Seller seller = Seller.builder().id(sellerId).name(sellerName).build();

        List<Buyer> buyerList = List.of(Buyer.builder().build(),Buyer.builder().build());

        FollowerCountDTO expected = new FollowerCountDTO(sellerId,sellerName, (long) buyerList.size());

        when(userRepository.existsSellerById(sellerId)).thenReturn(true);
        when(userRepository.getSellerById(sellerId)).thenReturn(seller);
        when(userRepository.getBuyersFollowingSeller(sellerId)).thenReturn(buyerList);

        //act

        FollowerCountDTO obtained = socialMeliService.calculateSellerFollowerCount(sellerId);

        //assert
        assertEquals(expected,obtained);
    }
    @DisplayName("US-0002 - Seller not found (T-0007)")
    @Test
    void calculateSellerFollowerCountNotFound(){
        //arrange
        Integer sellerId = 1;

        when(userRepository.existsSellerById(sellerId)).thenReturn(false);

        //act & assert
        assertThrows(NotFoundException.class, () -> socialMeliService.calculateSellerFollowerCount(sellerId));
    }

    @DisplayName("US-0002 - Happy path with many followers (T-0007)")
    @Test
    void calculateSellerFollowerCountEqual0(){
        //arrange
        Integer sellerId = 1;
        String sellerName = "Juan";
        Seller seller = Seller.builder().id(sellerId).name(sellerName).build();

        List<Buyer> buyerList = new ArrayList<>();

        FollowerCountDTO expected = new FollowerCountDTO(sellerId,sellerName, (long) buyerList.size());

        when(userRepository.existsSellerById(sellerId)).thenReturn(true);
        when(userRepository.getSellerById(sellerId)).thenReturn(seller);
        when(userRepository.getBuyersFollowingSeller(sellerId)).thenReturn(buyerList);

        //act
        FollowerCountDTO obtained = socialMeliService.calculateSellerFollowerCount(sellerId);

        //assert
        assertEquals(expected,obtained);
    }


}
package com.bootcamp.be_java_hisp_w29_g3.service;

import com.bootcamp.be_java_hisp_w29_g3.dto.response.FollowerCountDTO;
import com.bootcamp.be_java_hisp_w29_g3.entity.Buyer;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import com.bootcamp.be_java_hisp_w29_g3.exception.NotFoundException;
import com.bootcamp.be_java_hisp_w29_g3.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class Test0007 {
    @Mock
    IUserRepository userRepository;

    @InjectMocks
    SocialMeliServiceImpl service;

    @Test
    void calculateSellerFollowerCount(){
        //arrange
        Integer sellerId = 1;
        String sellerName = "Juan";
        Seller seller = Seller.builder().id(sellerId).name(sellerName).build();

        List<Buyer> buyerList = List.of(Buyer.builder().build(),Buyer.builder().build());

        FollowerCountDTO expected = new FollowerCountDTO(sellerId,sellerName, (long) buyerList.size());

        Mockito.when(userRepository.existsSellerById(sellerId)).thenReturn(true);
        Mockito.when(userRepository.getSellerById(sellerId)).thenReturn(seller);
        Mockito.when(userRepository.getBuyersFollowingSeller(sellerId)).thenReturn(buyerList);

        //act

        FollowerCountDTO obtained = service.calculateSellerFollowerCount(sellerId);

        //assert
        Assertions.assertEquals(expected,obtained);
    }

    @Test
    void calculateSellerFollowerCountNotFound(){
        //arrange
        Integer sellerId = 1;

        Mockito.when(userRepository.existsSellerById(sellerId)).thenReturn(false);

        //act & assert
        Assertions.assertThrows(NotFoundException.class, () -> service.calculateSellerFollowerCount(sellerId));
    }

    @Test
    void calculateSellerFollowerCountEqual0(){
        //arrange
        Integer sellerId = 1;
        String sellerName = "Juan";
        Seller seller = Seller.builder().id(sellerId).name(sellerName).build();

        List<Buyer> buyerList = new ArrayList<>();

        FollowerCountDTO expected = new FollowerCountDTO(sellerId,sellerName, (long) buyerList.size());

        Mockito.when(userRepository.existsSellerById(sellerId)).thenReturn(true);
        Mockito.when(userRepository.getSellerById(sellerId)).thenReturn(seller);
        Mockito.when(userRepository.getBuyersFollowingSeller(sellerId)).thenReturn(buyerList);

        //act
        FollowerCountDTO obtained = service.calculateSellerFollowerCount(sellerId);

        //assert
        Assertions.assertEquals(expected,obtained);
    }




}

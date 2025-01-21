package com.bootcamp.be_java_hisp_w29_g3.repository;

import com.bootcamp.be_java_hisp_w29_g3.entity.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements IUserRepository{
    private Map<Integer,Seller> sellers = new HashMap<>();
    private Map<Integer,Buyer> buyers = new HashMap();

    public UserRepositoryImpl(){
         loadDB();

    }

    public Map<Integer,Seller> getAll(){
        return sellers;
    }



    private void loadDB(){
        // Crear productos de prueba
        Product product1 = new Product(1, "Laptop Dell", "Electrónica", "Dell", "Negro", "Laptop de alta gama");
        Product product2 = new Product(2, "Smartphone Samsung", "Electrónica", "Samsung", "Azul", "Smartphone con excelente cámara");
        Product product3 = new Product(3, "Camisa Nike", "Ropa", "Nike", "Blanco", "Camisa deportiva");
        Product product4 = new Product(4, "Zapatos Adidas", "Ropa", "Adidas", "Rojo", "Zapatos cómodos para deporte");

        // Crear posts de prueba para vendedores
        Post post1 = new Post(1, LocalDate.of(2025, 1, 21), product1, 1, 1000.0, true, 10.0);
        Post post2 = new Post(2, LocalDate.of(2025, 1, 20), product2, 2, 600.0, false, 0.0);
        Post post3 = new Post(3, LocalDate.of(2025, 1, 22), product3, 3, 30.0, true, 5.0);
        Post post4 = new Post(4, LocalDate.of(2025, 1, 23), product4, 3, 50.0, false, 0.0);

        // Crear listas de posts para vendedores
        List<Post> postsSellerA = new ArrayList<>();
        postsSellerA.add(post1);
        postsSellerA.add(post2);

        List<Post> postsSellerB = new ArrayList<>();
        postsSellerB.add(post3);
        postsSellerB.add(post4);

        // Crear vendedores
        // Seller sellerA = new Seller(1, "Vendedor A", postsSellerA);
        // Seller sellerB = new Seller(2, "Vendedor B", postsSellerB);


        Seller sellerA = Seller.builder()
                .id(1)
                .name("Vendedor A")
                .posts(postsSellerA)
                .build();

        Seller sellerB = Seller.builder()
                .id(2)
                .name("Vendedor B")
                .posts(postsSellerB)
                .build();

        // Añadir vendedores al mapa
        sellers.put(sellerA.getId(), sellerA);
        sellers.put(sellerB.getId(), sellerB);

        // Crear compradores
        List<Seller> sellersBuyer1 = new ArrayList<>();
        sellersBuyer1.add(sellerA);  // Comprador 1 compra a Vendedor A

        List<Seller> sellersBuyer2 = new ArrayList<>();
        sellersBuyer2.add(sellerB);  // Comprador 2 compra a Vendedor B

        // Crear compradores
        // Buyer buyer1 = new Buyer(1, "Comprador X", sellersBuyer1);
        // Buyer buyer2 = new Buyer(2, "Comprador Y", sellersBuyer2);

        var buyer1 = Buyer.builder()
                .id(1)
                .name("Comprador X")
                .sellers(sellersBuyer1)
                .build();

        var buyer2 = Buyer.builder()
                .id(2)
                .name("Comprador Y")
                .sellers(sellersBuyer2)
                .build();

        // Añadir compradores al mapa
        buyers.put(buyer1.getId(), buyer1);
        buyers.put(buyer2.getId(), buyer2);

    }
}

package com.bootcamp.be_java_hisp_w29_g3.repository;

import com.bootcamp.be_java_hisp_w29_g3.entity.Buyer;
import com.bootcamp.be_java_hisp_w29_g3.entity.Post;
import com.bootcamp.be_java_hisp_w29_g3.entity.Product;
import com.bootcamp.be_java_hisp_w29_g3.entity.Seller;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private final Map<Integer, Seller> sellers = new HashMap<>();
    private final Map<Integer, Buyer> buyers = new HashMap<>();

    public UserRepositoryImpl() {
        loadDB();
    }

    @Override
    public Post addPostToSeller(Integer userId, Post newPost) {
        Seller seller = sellers.get(userId);
        List<Post> currentPosts = seller.getPosts();
        currentPosts.add(newPost);
        seller.setPosts(currentPosts);
        return newPost;
    }

    private void loadDB(){
        // Crear productos de prueba
        Product product1 = new Product(1, "Laptop Dell", "Electrónica", "Dell", "Negro", "Laptop de alta gama");
        Product product2 = new Product(2, "Smartphone Samsung", "Electrónica", "Samsung", "Azul", "Smartphone con excelente cámara");
        Product product3 = new Product(3, "Camisa Nike", "Ropa", "Nike", "Blanco", "Camisa deportiva");
        Product product4 = new Product(4, "Zapatos Adidas", "Ropa", "Adidas", "Rojo", "Zapatos cómodos para deporte");

        // Crear posts de prueba para vendedores
        Post post1 = new Post(1, LocalDate.of(2025, 1, 21), product1, 1, 1000.0, true, 10.0);
        Post post2 = new Post(2, LocalDate.of(2025, 1, 5), product2, 2, 600.0, false, 0.0);
        Post post3 = new Post(3, LocalDate.of(2025, 1, 22), product3, 3, 30.0, true, 5.0);
        Post post4 = new Post(4, LocalDate.of(2025, 1, 23), product4, 3, 50.0, false, 0.0);
        Post post5 = new Post(5, LocalDate.of(2025, 1, 24), product1, 3, 50.0, false, 0.0);

        // Crear listas de posts para vendedores
        List<Post> postsSellerA = new ArrayList<>();
        postsSellerA.add(post1);
        postsSellerA.add(post2);
        postsSellerA.add(post5);

        List<Post> postsSellerB = new ArrayList<>();
        postsSellerB.add(post3);
        postsSellerB.add(post4);

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
        sellersBuyer1.add(sellerB);  // Comprador 1 compra a Vendedor B

        List<Seller> sellersBuyer2 = new ArrayList<>();
        sellersBuyer2.add(sellerB);  // Comprador 2 compra a Vendedor B

        List<Seller> sellersBuyer3 = new ArrayList<>();
        sellersBuyer3.add(sellerA);

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

        Buyer buyer3 = Buyer.builder()
                .id(3)
                .name("Comprador Z")
                .sellers(sellersBuyer3)
                .build();

        // Añadir compradores al mapa
        buyers.put(buyer1.getId(), buyer1);
        buyers.put(buyer2.getId(), buyer2);
        buyers.put(buyer3.getId(), buyer3);
    }

    @Override
    public void followSeller(int userId, int userIdToFollow) {
        buyers.get(userId).getSellers().add(sellers.get(userIdToFollow));
    }

    @Override
    public void unfollowSeller(int userId, int userIdToUnfollow) {
        buyers.get(userId).getSellers().removeIf(seller -> seller.getId() == userIdToUnfollow);
    }

    //Verifico si existe el vendedor
    @Override
    public boolean existsSellerById(int userIdToFollow) {
        return sellers.containsKey(userIdToFollow);
    }

    //Verifico si existe el comprador
    @Override
    public boolean existsBuyerById(int userId) {
        return buyers.containsKey(userId);
    }

    //Verifico si el comprador ya seguia al vendedor
    @Override
    public boolean buyerAlreadyFollowsSeller(int userId, int userIdToFollow) {
        return buyers.get(userId).getSellers().stream().anyMatch(seller -> seller.getId() == userIdToFollow);
    }

    @Override
    public Buyer getBuyerById(Integer buyerId) {
        return buyers.get(buyerId);
    }

    @Override
    public Seller getSellerById(Integer userId) {
        return sellers.get(userId);
    }

    /* Se agrego el ordenamiento del punto 8
    public List<Seller> getSellersFollowedByBuyer(int userId) {
        Buyer buyer = buyers.get(userId);
        return buyer != null ? buyer.getSellers() : new ArrayList<>();
    }*/
    @Override
    public List<Seller> getSellersFollowedByBuyer(int userId, String order) {
        Buyer buyer = buyers.get(userId);
        List<Seller> sellers = buyer.getSellers();
        if ("name_asc".equalsIgnoreCase(order)) {
            sellers.sort(Comparator.comparing(Seller::getName));
        } else if ("name_desc".equalsIgnoreCase(order)) {
            sellers.sort(Comparator.comparing(Seller::getName).reversed());
        }
        return sellers;
    }

    @Override
    public Long countPromotionalProductsBySeller(int sellerId) {
        Seller seller = sellers.get(sellerId);
        if (seller != null) {
            return seller.getPosts().stream()
                    .filter(Post::getHasProm)
                    .count();
        }
        return 0L;
    }

    @Override
    public List<Buyer> getBuyersFollowingSeller(Integer sellerId) {
        return buyers.values().stream()
                .filter(buyer -> buyer.getSellers().stream()
                        .anyMatch(seller -> seller.getId().equals(sellerId)))
                .toList();
    }

    @Override
    public List<Buyer> getFollowers(int sellerId, String order) {
        List<Buyer> followers = buyers.values().stream()
                .filter(buyer -> buyer.getSellers().stream()
                        .anyMatch(seller -> seller.getId() == sellerId))
                .collect(Collectors.toCollection(ArrayList::new));

        if ("name_asc".equalsIgnoreCase(order)) {
            followers.sort(Comparator.comparing(Buyer::getName));
        } else if ("name_desc".equalsIgnoreCase(order)) {
            followers.sort(Comparator.comparing(Buyer::getName).reversed());
        }

        return followers;
    }

    @Override
    public List<Seller> getSellersByRangePrice(final Double minPrice, final Double maxPrice, final String productName) {
        List<Seller> sellersByPriceList = sellers.values()
                .stream()
                .filter(filterPostsByPriceRange(minPrice, maxPrice))
                .toList();

        return sellersByPriceList.stream()
                .filter(filterPostsByProductName(productName))
                .toList();
    }

    private static Predicate<Seller> filterPostsByPriceRange(Double minPrice, Double maxPrice) {
        return seller -> seller.getPosts()
                .stream()
                .filter(post -> (post.getPrice() >= minPrice && post.getPrice() <= maxPrice))
                .anyMatch(post -> (post.getPrice() >= minPrice && post.getPrice() <= maxPrice));
    }


    private static Predicate<Seller> filterPostsByProductName(String productName) {
        return seller -> seller.getPosts()
                .stream()
                .filter(post -> post.getProduct().getName().equalsIgnoreCase(productName))
                .anyMatch(post -> post.getProduct().getName().equalsIgnoreCase(productName));
    }

    public List<Integer> getAllSellersId() {
        return sellers.keySet().stream().toList();
    }

    @Override
    public List<Post> findPostsFromSellerByUserIdWithLimitDate(Integer userId, LocalDate limitDate, String order) {
        return buyers.get(userId).getSellers().stream()
                .flatMap(seller -> seller.getPosts().stream())
                .filter(seller -> seller.getDate().isAfter(limitDate))
                .sorted(getPostDateComparator(order))
                .toList();
    }

    private Comparator<Post> getPostDateComparator(String order){
        if(order == null || order.equalsIgnoreCase("date_desc")) {
            return Comparator.comparing(Post::getDate).reversed();
        }
        return Comparator.comparing(Post::getDate);
    }
}

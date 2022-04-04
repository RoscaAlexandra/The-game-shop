package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.ShoppingCart;
import com.proiecte.GamesStore.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {

    List<ShoppingCart> findByUserId(long id);
    ShoppingCart findById(int id);
    List<ShoppingCart> findByUserIdAndGame(long id, Game game);
    List<ShoppingCart> findByUser (User user);
    List<ShoppingCart> findByUserUsernameAndGame(String username, Game game);
    @Query("select c from ShoppingCart c where c.user.username = :username")
    Page<ShoppingCart> findAllPage(@Param("username") String userName, Pageable pageable);

    /*
    List<ShoppingCart> findByUserIdAndBook(int id, Game game);
    List<ShoppingCart> findByUserUsernameAndBook(String username, Game game);

    //   ShoppingCart findByUserIdAndOrderDate(int id, LocalDate date);
    ShoppingCart findById(int id);
    @Query("select c from ShoppingCart c where c.user.username = :username")
    Page<ShoppingCart> findAllPage(@Param("username") String userName, Pageable pageable);
    //ShoppingCart findByUserIdAndBooksIn(int id, List<Book> books);
    //   ShoppingCart findByOrderId(int id);
    // List<ShoppingCart> findByBooks(Book book);*/
}
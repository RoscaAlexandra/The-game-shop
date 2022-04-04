package com.proiecte.GamesStore.services;



import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.ShoppingCart;
import com.proiecte.GamesStore.domain.User;
import com.proiecte.GamesStore.dto.ResultDTO;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.repositories.ShoppingCartRepository;
import com.proiecte.GamesStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private GameService gameService;
    private GameRepository gameRepository;
    private UserRepository userRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, GameService gameService, GameRepository gameRepository, UserRepository userRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    public List<Game> getRandomElement(List<Game> list, int totalItems) {
        Random rand = new Random();

        List<Game> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
        }
        return newList;
    }

    private List<Game> randomizeBooks() {
        List<Game> games = new ArrayList<>();
        gameRepository.findAll().forEach(games::add);

        return games;
    }



    public void seedCarts() {
        seedCart(1, 4, userRepository.findByUsername("szmettix"), gameRepository.findAll().get(0));
        seedCart(2, 2, userRepository.findByUsername("szmettix"), gameRepository.findAll().get(1));
    }

    private void seedCart(int id, int quantity, User user, Game game) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart().setId(id).setQuantity(quantity).setUser(user).setGame(game);
            shoppingCartRepository.save(shoppingCart);
        }
    }


   /* public ShoppingCart addToShoppingCart(@RequestBody BookDTO book, Principal principal) {
        Book bok = new Book();
        bok.setId(book.getId())
                .setBookCategory(book.getBookCategory())
                .setBookInfo(book.getBookInfo())
                .setPrice(book.getPrice());

        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = shoppingCart.getBooks();
        books.add(bok);
        shoppingCart.setBooks(books);
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }

    public ResultDTO removeFromShoppingCart(@PathVariable("id") int id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        List<Book> books = shoppingCart.getBooks();
        books.remove(books.indexOf(bookRepository.findById(id)));
        shoppingCart.setBooks(books);
        shoppingCartRepository.save(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Book removed from cart.");
    }
    */

    public ResultDTO removeFromShoppingCart(@PathVariable("id") int id, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Game game = gameRepository.findById(id);
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserIdAndGame(user.getId(), game);

        shoppingCartRepository.deleteAll(shoppingCarts);
        return new ResultDTO().setType("success").setMessage("Game removed from cart.");
    }

    public List<ShoppingCart> getShoppingCarts(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        return shoppingCarts;
    }

    public List<Game> getBooksFromShoppingCart(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserId(user.getId());
        List<Game> games = new ArrayList<Game>();
        for (ShoppingCart shoppingCart : shoppingCarts)
            games.add(shoppingCart.getGame());
        return games;
//        return shoppingCartRepository.findByUserId(user.getId());
    }



    public ResultDTO deleteShoppingCart(String Name, Principal principal) {
        User  user=userRepository.findByUsername(Name);
        List<ShoppingCart> shoppingCartsdel=shoppingCartRepository.findByUser(user);
        for (ShoppingCart shoppingCart : shoppingCartsdel)
            shoppingCartRepository.delete(shoppingCart);
        return new ResultDTO().setType("success").setMessage("Game deleted.");
    }

    public List<Game> getAllGame(String Name, Principal principal) {
        User  user=userRepository.findByUsername(Name);
        List<ShoppingCart> shoppingCartsdel=shoppingCartRepository.findByUser(user);
        List <Game> games=new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartsdel){
            games.add(shoppingCart.getGame());
        }

        return games;
    }
}
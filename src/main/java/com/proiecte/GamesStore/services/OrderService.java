package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.Order;
import com.proiecte.GamesStore.domain.User;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.repositories.OrderRepository;
import com.proiecte.GamesStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    GameRepository gameRepository;
    //EmailService emailService;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository, GameRepository gameRepository /*EmailService emailService*/) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        //this.emailService = emailService;
    }

    public void createOrder(List<Game> games, Principal principal) throws IOException {

        LocalDate date = LocalDate.now();
        Double totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        for (Game game : games) {
            totalPrice += game.getPrice();
        }
        Order order = new Order();
        order.setOrderDate(date);
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order.setGames(games);


      /*  emailService.createPDF(books, principal);
        orderRepository.save(order);
        return new OrderDTO()
                .setOrderDate(order.getOrderDate())
                .setUser(order.getUser())
                .setBooks(order.getBooks())
                .setTotalPrice(order.getTotalPrice());*/
         orderRepository.save(order);
    }

}

package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Comanda;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.Order;
import com.proiecte.GamesStore.domain.User;
import com.proiecte.GamesStore.repositories.ComandaRepository;
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
public class ComandaService {
    ComandaRepository comandaRepository;
    UserRepository userRepository;
    GameRepository gameRepository;
    //EmailService emailService;

    @Autowired
    public ComandaService(ComandaRepository comandaRepository, UserRepository userRepository, GameRepository gameRepository /*EmailService emailService*/) {
        this.comandaRepository = comandaRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        //this.emailService = emailService;
    }

    public List<Comanda> getComandUser(User user){

        List <Comanda> comenzi=comandaRepository.findByUser(user);
        return  comenzi;
    }
    public void createCoamnda(List<Game> games, Principal principal) throws IOException {

        LocalDate date = LocalDate.now();
        Double totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        for (Game game : games) {
            totalPrice += game.getPrice();
        }
        Comanda comanda = new Comanda();
        comanda.setOrderDate(date);
        comanda.setTotalPrice(totalPrice);
        comanda.setUser(user);
        comanda.setGames(games);


      /*  emailService.createPDF(books, principal);
        orderRepository.save(order);
        return new OrderDTO()
                .setOrderDate(order.getOrderDate())
                .setUser(order.getUser())
                .setBooks(order.getBooks())
                .setTotalPrice(order.getTotalPrice());*/

        comandaRepository.save(comanda);
    }
}


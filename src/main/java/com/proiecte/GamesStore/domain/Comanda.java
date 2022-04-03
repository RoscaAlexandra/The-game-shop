package com.proiecte.GamesStore.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double totalPrice;

    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToMany
    @JoinTable(name = "comanda_games",
            joinColumns = {
                    @JoinColumn(name = "comanda_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "game_id")})
    private List<Game> games;
}

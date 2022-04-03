package com.proiecte.GamesStore.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Getter
@Setter
public class Order{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NumberFormat
    private Double totalPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @ManyToMany
    @JoinTable(name = "order_games",
            joinColumns = {
            @JoinColumn(name = "order_id")},
            inverseJoinColumns = {
            @JoinColumn(name = "game_id")})
    private List<Game> games;




}

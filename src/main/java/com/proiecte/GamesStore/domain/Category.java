package com.proiecte.GamesStore.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "gameCategory")
    private List<Game> games;


    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }
}

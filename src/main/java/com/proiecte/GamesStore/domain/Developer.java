package com.proiecte.GamesStore.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String descriere;

    @ManyToMany(mappedBy = "developers")
    private List<Game> games;

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                '}';
    }
}

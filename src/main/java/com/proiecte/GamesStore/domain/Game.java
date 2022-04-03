package com.proiecte.GamesStore.domain;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"orders", "shoppingCarts"})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NumberFormat
    private Double price;


    @OneToOne(fetch = FetchType.LAZY ,orphanRemoval =true, cascade={ALL})
    @JoinColumn(name="game_info_id")
    private GameInfo gameInfo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "category_game",
            joinColumns = {
                    @JoinColumn(name = "game_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    private List<Category> gameCategory;



    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "developer_game",
            joinColumns = {
                    @JoinColumn(name = "game_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "developer_id")
            }
    )
    private List<Developer> developers;

    @ManyToMany(mappedBy = "games")
    private List<Order> orders;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<ShoppingCart> shoppingCarts;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", price=" + price +
                ", gameInfo=" + gameInfo +
                ", gameCategory=" + gameCategory +
                ", developers=" + developers +
                ", orders=" + orders +
                ", shoppingCarts=" + shoppingCarts +
                '}';
    }
}

package com.proiecte.GamesStore.repositories;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByDevelopers(Developer developer);
    List<Game> findAll();
    Game findById(long Id);
    Game findByGameInfoAndPriceAndGameCategory (GameInfo gameInfo,double price,Category category);
    Game findByGameInfo(GameInfo gameInfo);
    Game findByGameInfoAndPrice(GameInfo gameInfo,double price);
    List<Game> findByGameCategory(Category category);

    Page <Game> findByDevelopers(Developer dev, Pageable pageable);

    @Query("select c from Game c")
    Page<Game> findAllPage(Pageable pageable);

}

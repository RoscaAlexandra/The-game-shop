package com.proiecte.GamesStore.services;


import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class GameServiceTests {


    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameInfoRepository gameInfoRepository;

    @Test
    public void getGame(){
        Game game = gameService.getGame("LOL");
        System.out.print(game);
    }

    @Test
    public  void getAllGamesByCategory(){
        Category gameCategory = new Category();
        gameCategory.setId((long)3);
        gameCategory.setName("Action");
        List<Game> games = gameService.getAllGamesByCategory(gameCategory);
        log.info("getGamesByCategory...");
        games.forEach(game -> log.info(game.getGameInfo().getName()));
    }

    @Test
    public void editBook() {
        Game game = new Game();
        game.setPrice(88.8);
        Game editedGame = gameService.editGame(1, game);
        System.out.print(" Noul rezultat " + editedGame);
    }

    @Test
    public void deleteBook () {
        gameService.deleteGame((long)1);
        List<Game> games = gameRepository.findAll();
        log.info("Books from repository ");
        games.forEach(game -> log.info(game.getGameInfo().getName()));
    }

    @Test
    public void createBook() {
        Game game = new Game();
        Category gameCategory = new Category();
        gameCategory.setId((long)1);
        gameCategory.setName("Action");
        List<Category> categories=new ArrayList<>();
        categories.add(gameCategory);
        game.setId(6);
        game.setPrice(17.45);
        game.setGameCategory(categories);
        GameInfo gameInfo = new GameInfo();

        gameInfo.setId(Long.valueOf(6));
        gameInfo.setName("LOL");
        gameInfoRepository.save(gameInfo);

        List<Developer> developers = new ArrayList<>(1);
        Developer developer = new Developer();
        developer.setId(3);
        developer.setName("RIOT");

        developers.add(developer);
        game.setGameInfo(gameInfo);
        game.setDevelopers(developers);

        Game game1= gameService.createGame(game);
        List<Game> games = gameRepository.findAll();
        log.info("Games from repository ");
        for(Game game0:games){
            game.getGameCategory().forEach(category -> log.info(category.getName()));
        }

    }

    @Test
    public void getGamesByDeveloper(){
        Developer developer = new Developer();
        developer.setId(3);
        developer.setName("ES");
        List<Game> games = gameRepository.findByDevelopers(developer);
        log.info("Game from repository ");
        games.forEach(game -> log.info(game.getGameInfo().getName()));

    }
}

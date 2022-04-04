package com.proiecte.GamesStore.repository;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.repositories.GameRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
@Transactional
@Slf4j
public class GameRepositoryTests {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void findByDeveloper() {
        Developer developer = new Developer();
        List<Game> gameList = new ArrayList<>();
        gameList.add(gameRepository.findById(1));
        developer.setId(1);
        developer.setName("ES");
        developer.setGames(gameList);
        List<Game> books = gameRepository.findByDevelopers(developer);
        log.info("findByAuthors...");
        books.forEach(game -> log.info(game.getGameInfo().getName()));

    }

    @Test
    public void findByGameCategory() {
        Category gameCtageory = new Category();
        gameCtageory.setId((long)1);
        gameCtageory.setName("Test");
        List<Game> books = gameRepository.findByGameCategory(gameCtageory);
        log.info("findByCategory...");
        books.forEach(game -> log.info(game.getGameInfo().getName()));

    }

    @Test
    public void findByGameInfo() {
        GameInfo gameInfo = new GameInfo();
        gameInfo.setId((long)3);
        gameInfo.setName("Test");
        gameInfo.setDescription("Test");
        gameInfo.setDate(LocalDate.of(2011,04,22));

        Game game = gameRepository.findByGameInfo(gameInfo);
        System.out.print(gameInfo);

    }

    @Test
    public void findAllPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Game> page = gameRepository.findAllPage(pageable);
        log.info("findByPage...");
        page.forEach(pg -> log.info(pg.getGameInfo().getName()));

    }

}

package com.proiecte.GamesStore.repository;

import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;
@SpringBootTest
@Transactional
@Slf4j
public class DeveloperRepositoryTests {


    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void findByName(){
        Developer developer = developerRepository.findByName("VALVE");
        System.out.print("Developer: " + developer);
    }

    @Test
    public void findByGame(){
        Game game = gameRepository.findById(5);
        List<Developer> developers = developerRepository.findByGames(game);
        log.info("findByGamess...");
        developers.forEach(developer -> log.info(developer.getName()));
    }

    @Test
    public void findAllPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Developer> page = developerRepository.findAllPage(pageable);
        log.info("findByPage...");
        page.forEach(pg -> log.info(pg.getName()));

    }


}


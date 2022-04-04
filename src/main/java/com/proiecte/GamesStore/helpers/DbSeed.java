package com.proiecte.GamesStore.helpers;

import com.proiecte.GamesStore.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DbSeed implements CommandLineRunner {
    @Autowired
    private CategoryService gameCategoryService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private GameService gameService;

    @Autowired
   private GameInfoService gameInfoService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;
    @Override
    @Transactional
    public void run(String... args) {
        userService.seedUsers();
        gameInfoService.seedGameInfo();
        gameCategoryService.seedGameCategories();
        developerService.seedDeveloper();
        gameService.seedGames();
       shoppingCartService.seedCarts();

    }

}

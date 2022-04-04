package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.dto.ResultDTO;
import com.proiecte.GamesStore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private GameRepository gameRepository;
    private CategoryRepository categoryRepository;
    private GameInfoRepository gameInfoRepository;
    private DeveloperRepository developerRepository;
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;


    public List<Game> findAll() {
        return  gameRepository.findAll();
    }

    @Autowired
    public GameService(
            GameRepository gameRepository,
            CategoryRepository categoryRepository,
            GameInfoRepository gameInfoRepository,
            DeveloperRepository developerRepository,
            OrderRepository orderRepository,
            ShoppingCartRepository shoppingCartRepository
    ) {
        this.gameRepository = gameRepository;
        this.categoryRepository = categoryRepository;
        this.gameInfoRepository = gameInfoRepository;
        this.developerRepository = developerRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    private void seedBook(Double price, GameInfo gameInfo, List <Developer> developers, List<Category> gameCategory) {
        Game game = gameRepository.findByGameInfoAndPrice(
                gameInfo, price
        );
        if (game == null) {
            Game newGame = new Game();
            newGame.setPrice(price);
            newGame.setGameInfo(gameInfo);
            newGame.setGameCategory(gameCategory);
            newGame.setDevelopers(developers);
            gameRepository.save(newGame);
        }
    }

    public void seedGames() {

        ArrayList<Long> categoryId = new ArrayList<Long>();
        categoryId.add((long)1);
        categoryId.add((long)2);


        seedBook(
                35.55,
                gameInfoRepository.findByName("CS_GO"),
                (List<Developer>) developerRepository.findAllById(Collections.singleton(1)),
                (List<Category>) categoryRepository.findAllById(categoryId)

        );

        seedBook(
                100.55,
                gameInfoRepository.findByName("LOL"),
                (List<Developer>) developerRepository.findAllById(Collections.singleton(2)),
                (List<Category>) categoryRepository.findAllById(categoryId)
        );





    }



    public Game getGame (String title){
        GameInfo gameInfo = gameInfoRepository.findGameInfoByName(title);
        Game game = gameRepository.findByGameInfo(gameInfo);
        return game ;
    }
    public Game cautaById(Long l) {
        Optional<Game> productOptional = gameRepository.findById(l);
        if (!productOptional.isPresent()) {
            //throw new ResourceNotFoundException("product " + l + " not found");
        }
        return productOptional.get();
    }
    public List<Game> getAllGamesByCategory (Category gameCategory){
        List<Game> listOfGames = gameRepository.findByGameCategory(gameCategory);
        List<Game> listOfAllGames = new ArrayList<>();
        /*for(int i = 0; i < listOfAllGames.size(); i++)
            listOfAllGames.add(new Game(listOfGames.get(i)));*/
        return listOfGames;
    }


    public List<Game> getGamesByDeveloper (Developer developer){
        List<Game> listofGames = gameRepository.findByDevelopers(developer);
        /*List<BookDTO> listOfAllBooks = new ArrayList<>();
        for(int i = 0; i < listOfBooks.size(); i++)
            listOfAllBooks.add(new BookDTO(listOfBooks.get(i)));*/
        return listofGames;
    }


    public Game createGame(Game game) {
        Game newGame = new Game();
        newGame.setPrice(game.getPrice());
        newGame.setDevelopers(game.getDevelopers());
        newGame.setGameCategory(game.getGameCategory());
        newGame.setGameInfo(game.getGameInfo());

        return gameRepository.save(newGame);
    }


    public Game editGame(long id, Game game) {
        Game updatedGame = gameRepository.findById(id);
        updatedGame.setPrice(game.getPrice());
        updatedGame.setDevelopers(game.getDevelopers());
        updatedGame.setGameCategory(game.getGameCategory());
        updatedGame.setGameInfo(game.getGameInfo());
        return updatedGame;
    }

    public ResultDTO deleteGame(Long id) {
        Optional<Game> gameDel = gameRepository.findById(id);
        if (gameDel.isPresent()) {
            gameRepository.deleteById(id);
            return new ResultDTO().setType("success").setMessage("Game  deleted.");
        }
        return new ResultDTO().setType("fail").setMessage("Game  does not exists");
    }




}
package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.dto.ResultDTO;
import com.proiecte.GamesStore.repositories.DeveloperRepository;
import com.proiecte.GamesStore.repositories.GameInfoRepository;
import com.proiecte.GamesStore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DeveloperService {


    private DeveloperRepository developerRepository;
    private GameRepository gameRepository;
    private GameInfoRepository gameInfoRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository, GameRepository gameRepository, GameInfoRepository gameInfoRepository) {
        this.developerRepository = developerRepository;
        this.gameRepository = gameRepository;
        this.gameInfoRepository = gameInfoRepository;
    }

    public void seedDeveloper() {
        seedDeveloper("ES","Descriere1");
        seedDeveloper("VALVE","Descriere2");
        seedDeveloper("ROT","Descriere1");
    }

    public void seedDeveloper(String name, String descriere){
        Developer developer = developerRepository.findByName(name);

        if(developer == null) {
            Developer newDeveloper = new Developer();
            newDeveloper.setDescriere(descriere);
            newDeveloper.setName(name);
            developerRepository.save(newDeveloper);
        }
    }

/*
    public List<Author> getAllAuthors() {
        return (List<Author>) authorRepository.findAll();
    }

 */

    public Set<Developer> getAllDevelopers() {
        Set<Developer> authors = new HashSet<>();
        developerRepository.findAll().iterator().forEachRemaining(authors::add);
        return authors;
    }

    public Developer createDeveloper(Developer developer) {
        Developer newDeveloper = new Developer();
        newDeveloper.setName(developer.getName());
        newDeveloper.setDescriere(developer.getDescriere());
        newDeveloper.setGames(developer.getGames());
        developerRepository.save(newDeveloper);
        return  newDeveloper;
    }

  /*  public AuthorDTO editAuthor(int id, String firstName, String lastName) {
        Author updatedAuthor = authorRepository.findById(id);
        Author newAuthor = new Author(id,firstName,lastName);
    //    newAuthor.setFirstName(newAuthor.getFirstName()).setLastName(newAuthor.getLastName()).setBooks(newAuthor.getBooks());
        authorRepository.save(newAuthor);
        return new AuthorDTO(updatedAuthor);
    }*/

    public Developer editDeveloper(int id, Developer developer) {
        Developer updatedDeveloper= developerRepository.findById(id);
        updatedDeveloper.setName(developer.getName());
        updatedDeveloper.setDescriere(developer.getDescriere());
        updatedDeveloper.setGames(developer.getGames());

        developerRepository.save(updatedDeveloper);
        return updatedDeveloper;
    }


    public int numberOfDeveloperGamesBook(String title){
        GameInfo gameInfo = gameInfoRepository.findGameInfoByName(title);
        Game game = gameRepository.findById(gameInfo.getId()).get();
        List<Developer> developersOFbook = developerRepository.findByGames(game);
        return developersOFbook.size();
    }

    public ResultDTO deleteDeveloper(int id) {
        Developer deleteDeveloper = developerRepository.findById(id);
        List<Game> listOfBooks = gameRepository.findByDevelopers(deleteDeveloper);
        List<Game> gamesToDelete = new ArrayList<Game>();

        for (Game game : listOfBooks) {
            List<Developer> authors = game.getDevelopers();
            if (authors.size() == 1)
                gamesToDelete.add(game);
        }


        developerRepository.delete(deleteDeveloper);
        gameRepository.deleteAll(gamesToDelete);

        return new ResultDTO().setType("success").setMessage("Developer deleted.");
    }


}

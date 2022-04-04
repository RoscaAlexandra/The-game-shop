package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.repositories.GameInfoRepository;
import com.proiecte.GamesStore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GameInfoService {

    private GameInfoRepository gameInfoRepository;
    private GameRepository gameRepository;

    @Autowired
    public GameInfoService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    public void seedGameInfo() {
        seedGameInfo("CS_GO","Joc de CODATI",LocalDate.of(1933, 8, 13));
        seedGameInfo("LOL","Nu te juca",LocalDate.of(1933, 8, 13));
    }

    private void seedGameInfo(String nume, String descriere, LocalDate date) {
        GameInfo gameInfo = gameInfoRepository.findByName(nume);
        if (gameInfo == null) {
            gameInfo = new GameInfo();
            gameInfo   .setName(nume);
            gameInfo   .setDescription(descriere);
            gameInfo   .setDate(date);
            gameInfoRepository.save(gameInfo);
        }
    }

    public GameInfo getGameInfoById (int Id){
        GameInfo gameInfo = gameInfoRepository.findById(Id);
        return gameInfo;
    }

    public List<GameInfo> getAll() {
        return gameInfoRepository.findAll();
    }
/*
    public BookInfoDTO createBookInfo(BookInfoDTO bookInfo) {
        BookInfo newBookInfo = new BookInfo()
                .setBookISBN(bookInfo.getBookISBN())
                .setBookPublicationDate(bookInfo.getBookPublicationDate())
                .setBookTitle(bookInfo.getBookTitle());
        return new BookInfoDTO(bookInfoRepository.save(newBookInfo));
    }

    public BookInfoDTO editBookInfo(int id, BookInfoDTO bookInfo) {
        BookInfo updatedBookInfo = bookInfoRepository.findById(id);
        updatedBookInfo.setBookTitle(bookInfo.getBookTitle())
                .setBookPublicationDate(bookInfo.getBookPublicationDate())
                .setBookISBN(bookInfo.getBookISBN());
        bookInfoRepository.save(updatedBookInfo);
        return new BookInfoDTO(updatedBookInfo);
    }

    public ResultDTO deleteBookInfo(int id) {
        BookInfo deleteBookInfo = bookInfoRepository.findById(id);
        bookInfoRepository.delete(deleteBookInfo);
        Book deleteBook = bookRepository.findById(id);
        bookRepository.delete(deleteBook);
        return new ResultDTO().setType("success").setMessage("Book deleted.");
    }*/
}
package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    GameRepository gameRepository;

    @Autowired
    public ImageService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void saveImageFile(Long gameId, MultipartFile file) {
        try {
            Game game = gameRepository.findById(gameId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];
            int i = 0; for (byte b : file.getBytes()){
                byteObjects[i++] = b; }

            GameInfo info = game.getGameInfo();
            if (info == null) {
                info = new GameInfo();
            }

            info.setImage(byteObjects);
            game.setGameInfo(info);
            gameRepository.save(game); }
        catch (IOException e) {
        }
    }

}

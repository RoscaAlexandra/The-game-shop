package com.proiecte.GamesStore.controllers;


import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.GameInfo;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.services.GameService;
import com.proiecte.GamesStore.services.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/gameImage")
public class ImageController {

    private  GameService gameService;
    private GameRepository gameRepository;

    public ImageController(@Autowired ImageService imageService, @Autowired GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping(value = "getimage/{id}")
    public void downloadImage(@PathVariable String id, HttpServletResponse response) throws IOException {

        Game game = gameService.cautaById(Long.valueOf(id));

        System.out.println("klll");
        if (game.getGameInfo() != null) {
            GameInfo info = game.getGameInfo();

            if (game.getGameInfo().getImage() != null) {
                byte[] byteArray = new byte[info.getImage().length];
                int i = 0;
                for (Byte wrappedByte : info.getImage()) {
                    byteArray[i++] = wrappedByte;
                }
                response.setContentType("image/jpg");
                InputStream is = new ByteArrayInputStream(byteArray);
                try {
                    IOUtils.copy(is, response.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("HEEEi");
        }
    }
}
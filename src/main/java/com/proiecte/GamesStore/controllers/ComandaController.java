package com.proiecte.GamesStore.controllers;

import com.proiecte.GamesStore.domain.Comanda;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.domain.User;
import com.proiecte.GamesStore.repositories.ComandaRepository;
import com.proiecte.GamesStore.repositories.UserRepository;
import com.proiecte.GamesStore.services.ComandaService;
import com.proiecte.GamesStore.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/comanda")
public class ComandaController {
    @Autowired
    private ComandaService comandaService;
    @Autowired
    private ComandaRepository comandaRepository;
    @Autowired
    private UserRepository userRepository;



    @RequestMapping(value ="comenziList/page/{page}/{user}")
    public String listBooksPageByPage(Model model, @PathVariable("page") int pg,@PathVariable("user") String us) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));
        User user=userRepository.findByUsername(us);
        Page<Comanda> comandPage = comandaRepository.findByUserId(user.getId(),pageable);
        int totalPages = comandPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //model.addAttribute("activeGameList", true);
        model.addAttribute("comenzi", comandPage.getContent());
        return "orderView";
    }
    @RequestMapping(value ="gameOrder/{idComanda}/{user}")
    public String getGamesOrder(Model model, @PathVariable("idComanda") int idCom,@PathVariable("user") String us) {
        User user=userRepository.findByUsername(us);
        List<Comanda> comenzi=comandaRepository.findByIdAndUser(idCom,user);
        ArrayList<Game> games=new ArrayList<>();
        for(Comanda com:comenzi){
           for(Game game:com.getGames()){
               games.add(game);
           }
        }


        //model.addAttribute("activeGameList", true);
        model.addAttribute("games", games);
        return "orderGame";
    }
}

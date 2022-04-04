package com.proiecte.GamesStore.controllers;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.repositories.CategoryRepository;
import com.proiecte.GamesStore.repositories.DeveloperRepository;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.services.GameService;
import com.proiecte.GamesStore.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    ImageService imageService;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private CategoryRepository gameCategoryRepository;

    public GameController(ImageService imageService, GameService gameService, GameRepository gameRepository, DeveloperRepository developerRepository, CategoryRepository gameCategoryRepository) {
        this.imageService = imageService;
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.developerRepository = developerRepository;
        this.gameCategoryRepository = gameCategoryRepository;
    }

    @Autowired
    @RequestMapping(value="/*")
    public String homePage(){
        return "home";
    }

    @RequestMapping(value="/login")
    public String login(){
        return "login";
    }


    @RequestMapping(value="/games",method= RequestMethod.GET)
    public @ResponseBody
    List<Game> gameRest(){
        return (List<Game>) gameRepository.findAll();
    }




    @RequestMapping(value ="gameList/page/{page}")
    public String listGamesPageByPage(Model model, @PathVariable("page") int pg) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));
        Page<Game> gamePage = gameRepository.findAllPage(pageable);
        int totalPages = gamePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //model.addAttribute("activeGameList", true);
        model.addAttribute("games", gamePage.getContent());
        return "gameList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public  Game createGame(@RequestBody Game newGame) {
        return gameService.createGame(newGame);
    }

    //Add a new book
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String addBook(Model model){
        model.addAttribute("game", new Game());
        model.addAttribute("developers", developerRepository.findAll());
        model.addAttribute("gameCategories", gameCategoryRepository.findAll());
        return "addGame";
    }

    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }

    //Save a book
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/save")
    public String saveGame(Game game, @RequestParam("imagefile") MultipartFile file){
       Game game1= gameRepository.save(game);
        imageService.saveImageFile(game1.getId(), file);
        return "redirect:gameList/page/1";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/update/{id}")
    public String editGame(Game game, BindingResult bindingResult,@RequestParam("imagefile") MultipartFile file){
        Game gam1=  gameRepository.save(game);
        imageService.saveImageFile(gam1.getId(), file);
        return "redirect:/game/gameList/page/1";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/edit/{id}",method=RequestMethod.GET)
    public String editGame(@PathVariable("id") long id, Model model)  {
       Game game1 = gameService.cautaById(id);

       Game gam1=new Game();
       gam1.setId(id);
       gam1.setPrice(game1.getPrice());
       gam1.setGameInfo(game1.getGameInfo());
        model.addAttribute("game",gam1);
        model.addAttribute("developers", developerRepository.findAll());
        model.addAttribute("gameCategories", gameCategoryRepository.findAll());
        return "editGame";
    }
    @PreAuthorize("permitAll()")
    @RequestMapping (value="/gameInfo/{id}", method=RequestMethod.GET)
    public String getOnaeGame(@PathVariable("id") long id, Model model) {

        Game game1 = gameRepository.findById(id);
        model.addAttribute("game", game1);

        return "gameInfo";
    }
    @PreAuthorize("permitAll()")
    @RequestMapping (value="/gamesfromCategory/{id}", method=RequestMethod.GET)
    public String getOneGame(@PathVariable("id") long id, Model model) {
        Category cat=gameCategoryRepository.findById(id);
        List<Game> games=gameRepository.findByGameCategory(cat);

        model.addAttribute("games", games);

        return "gameList";
    }




    @PreAuthorize("permitAll()")
    @GetMapping("/allByCategory")
    public List<Game> getAllGamesByCategory(@RequestBody Category gameCategory){
        return gameService.getAllGamesByCategory(gameCategory);
    }


    @GetMapping("")
    public Game getGame(@RequestBody String name){
        return gameService.getGame(name);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/allDeveloper")
    public List<Game> getGamesByDeveloper(@RequestBody Developer developer){
        return gameService.getGamesByDeveloper(developer);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/delete/{id}", method=RequestMethod.GET)
    public String deleteGame(@PathVariable("id") Long id, Model model){
        gameService.deleteGame(id);
        return "redirect:/game/gameList/page/1";
    }

}

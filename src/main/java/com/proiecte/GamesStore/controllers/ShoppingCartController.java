package com.proiecte.GamesStore.controllers;

import com.proiecte.GamesStore.domain.*;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.repositories.ShoppingCartRepository;
import com.proiecte.GamesStore.repositories.UserRepository;
import com.proiecte.GamesStore.services.ComandaService;
import com.proiecte.GamesStore.services.OrderService;
import com.proiecte.GamesStore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller

@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartController shoppingCartController;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ComandaService comandaService;


  /*  @GetMapping("/add/viewCart")
    public String getAll(Principal principal, Model model) {
        //System.out.print(shoppingCartService.getBooksFromShoppingCart(principal));
        //model.addAttribute("booksFromShoppingCart", shoppingCartService.getBooksFromShoppingCart(principal));
        List<ShoppingCart> shoppingCarts= shoppingCartService.getShoppingCarts(principal);
        model.addAttribute("shoppingCarts", shoppingCarts);
        model.addAttribute("totalPrice",shoppingCartController.totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }*/

  /*  @PreAuthorize("permitAll()")
    @RequestMapping(value="/shoppingCartList")
    public String shoppingCartList(Model model){
        model.addAttribute("shoppingCarts", shoppingCartRepository.findAll());
        return "viewShoppingCart";
    }*/


    @RequestMapping(value ="shoppingCartList/page/{page}")
    public String listShoppingPageByPage(Model model, @PathVariable ("page") int pg,Principal principal) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("quantity"));
        User user = userRepository.findByUsername(principal.getName());
        Page<ShoppingCart> shoppingCartPage = shoppingCartRepository.findAllPage(user.getUsername(),pageable);
        int totalPages = shoppingCartPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activeShoppingList", true);
        model.addAttribute("shoppingCarts", shoppingCartPage.getContent());
        model.addAttribute("totalPrice",shoppingCartController.totalPriceShoppingCart(principal, model));
        return "viewShoppingCart";
    }

    public String totalPriceShoppingCart(Principal principal, Model model){
        Double  price;
        int     quantity;
        Double  totalPrice = 0.0;
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCarts= shoppingCartRepository.findByUserId(user.getId());
        for (ShoppingCart shoppingCart : shoppingCarts)
        {
            price = shoppingCart.getGame().getPrice();
            quantity = shoppingCart.getQuantity();
            totalPrice += price * quantity;
        }
        //model.addAttribute("totalPrice", totalPrice);
        String formattedTotalPrice = String.format("%.02f", totalPrice);
        return formattedTotalPrice;
    }

    @RequestMapping (value="/add/{id}", method=RequestMethod.GET)
    public String addToShoppingCart(@PathVariable("id") long id, Principal principal, Model model){
        Game game = gameRepository.findById(id);
        User user = userRepository.findByUsername(principal.getName());
        List<ShoppingCart> shoppingCartList= shoppingCartRepository.findByUserUsernameAndGame(user.getUsername(),game);
        if (shoppingCartList.isEmpty()) {
            ShoppingCart shoppingCart = new ShoppingCart().setQuantity(1).setGame(game).setUser(user);
            shoppingCartRepository.save(shoppingCart);
        } else {
            int quantity = shoppingCartList.get(0).getQuantity()+1;
            ShoppingCart shoppingCartOld = shoppingCartList.get(0);
            shoppingCartRepository.delete(shoppingCartOld);
            ShoppingCart shoppingCartNew = shoppingCartList.get(0).setQuantity(quantity).setGame(game).setUser(user);
            shoppingCartRepository.save(shoppingCartNew);
        }
        return "redirect:/game/gameList/page/1";
    }


    @RequestMapping (value="/delete/{id}", method=RequestMethod.GET)
    public String removeGameFromShoppingCart(@PathVariable("id") int id, Principal principal){
        shoppingCartService.removeFromShoppingCart(id,principal);
        return "redirect:/shoppingCart/shoppingCartList/page/1?";
    }

    @RequestMapping (value="/order/{stringName}", method=RequestMethod.GET)
    public String deleteShoppingCart(@PathVariable("stringName") String stringName, Principal principal) throws IOException {
        List<Game> games=shoppingCartService.getAllGame(stringName,principal);
        comandaService.createCoamnda(games,principal);
        shoppingCartService.deleteShoppingCart(stringName,principal);
        return "viewOrder";
    }
}



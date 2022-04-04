package com.proiecte.GamesStore.controllers;


import com.proiecte.GamesStore.domain.Developer;
import com.proiecte.GamesStore.domain.Game;
import com.proiecte.GamesStore.repositories.DeveloperRepository;
import com.proiecte.GamesStore.repositories.GameRepository;
import com.proiecte.GamesStore.services.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private GameRepository gameRepository;
    @RequestMapping(value ="developerList/page/{page}")
    public String listDeveloperPageByPage(Model model, @PathVariable("page") int pg) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("name"));
        Page<Developer> developrPage = developerRepository.findAllPage(pageable);
        int totalPages = developrPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("activeDeveloperList", true);
        model.addAttribute("developers", developrPage.getContent());
        return "developerList";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="developerList/add",method= RequestMethod.GET)
    public String addDeveloper(Model model){
        model.addAttribute("developer", new Developer());
        return "addDeveloper";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/save", method=RequestMethod.POST)
    public String saveDeveloper(Developer developer, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            if (developerRepository.findByName(developer.getName())==null){
                if(Name(developer.getName()))
                    developerRepository.save(developer);
                else  {bindingResult.rejectValue("name","err.N=name", "Invalid name");
                    return "addDeveloper";}
            }
            else {
                bindingResult.rejectValue("name","err.name", "Developer already exist");
                return "addDeveloper";
            }
        }
        else {
            return "addDeveloper";
        }
        return "redirect:/developer/developerList/page/1";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/save/test", method=RequestMethod.POST)
    public String saveDeveloper(Developer developer){
        developerRepository.save(developer);
        return "redirect:/developer/developerList/page/1";
    }

    public static boolean Name( String name ) {
        return name.matches( "[A-Z][a-z]*" );}



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editDeveloper(@PathVariable(value = "id") int id, Model model) {
        Developer developer = developerRepository.findById(id);
        model.addAttribute("developer" , developer);
        return "editDeveloper";
    }
    @GetMapping("/games/page/{page}/{id}")
    public String gameDeveloper(Model model, @PathVariable("page") int pg,@PathVariable(value = "id") int id) {

        Developer developer = developerRepository.findById(id);
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));
        Page<Game> gamePage = gameRepository.findByDevelopers(developer,pageable);
        int totalPages = gamePage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("games", gamePage.getContent());
        return "gameList";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String editDeveloper(Developer developer, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            if (developerRepository.findByName(developer.getName())==null){
                if(Name(developer.getName()))
                    developerRepository.save(developer);
                else  {bindingResult.rejectValue("name","err.name", "Invalid name");
                    return "editDeveloper";}
            }
            else {
                bindingResult.rejectValue("name","err.name", "Developer already exist");
                return "editDeveloper";
            }
        }
        else {
            return "editDeveloper";
        }
        return "redirect:/developer/developerList/page/1";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/delete/{id}",method=RequestMethod.GET)
    public String deleteDeveloper(@PathVariable("id") int id,Model model){
        developerService.deleteDeveloper(id);
        return "redirect:/developer/developerList/page/1";
    }
    @RequestMapping(value="/update/{id}/test",method=RequestMethod.GET)
    public String editDeveloper(Developer developer){
        developerRepository.save(developer);
        return "redirect:/developer/developerList/page/1";

    }
}

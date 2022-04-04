package com.proiecte.GamesStore.controllers;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.repositories.CategoryRepository;
import com.proiecte.GamesStore.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService gameCategoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryService gameCategoryService) {
        this.gameCategoryService = gameCategoryService;
    }


    @GetMapping("")
    public List<Category> getAll() {
        return gameCategoryService.getAllCategories();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/delete/{id}",method=RequestMethod.GET)
    public String deleteDeveloper(@PathVariable("id") long id,Model model){
        categoryRepository.deleteById(id);
        return "redirect:/categories/categoryList/page/1";
    }

    @RequestMapping(value="/update/{id}")
    public String updateCategory(Category category, BindingResult bindingResult){
          categoryRepository.save(category);
        return "redirect:/categories/categoryList/page/1";
    }

    @RequestMapping(value ="categoryList/page/{page}")
    public String listCategoryPageByPage(Model model, @PathVariable("page") int pg) {
        PageRequest pageable = PageRequest.of(pg - 1, 2, Sort.by("id"));
        Page<Category> categoryPage = categoryRepository.findAllPage(pageable);
        int totalPages = categoryPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        //model.addAttribute("activeGameList", true);
        model.addAttribute("categories", categoryPage.getContent());
        return "categoriesList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public Category createCategory(@RequestBody Category newCategory) {
        return gameCategoryService.createGameCategory(newCategory.getName());
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable(value = "id") long id, Model model) {
        Category category = categoryRepository.findById(id);
        model.addAttribute("category" , category);
        return "editCategories";
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="categoryList/add",method=RequestMethod.GET)
    public String addBook(Model model){
        model.addAttribute("category", new Category());

        return "addCategory";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping (value="/save", method=RequestMethod.POST)
    public String saveCategory(Category category, BindingResult bindingResult){
        if (!bindingResult.hasErrors()) {
            if (categoryRepository.findByName(category.getName())==null){

                categoryRepository.save(category);

            }
            else {
                bindingResult.rejectValue("name","err.name", "Category already exist");
                return "addCategory";
            }
        }
        else {
            return "addCategory";
        }
        return "redirect:/categories/categoryList/page/1";
    }

}
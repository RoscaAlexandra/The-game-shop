package com.proiecte.GamesStore.services;

import com.proiecte.GamesStore.domain.Category;
import com.proiecte.GamesStore.dto.ResultDTO;
import com.proiecte.GamesStore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository gameCategoryRepository;

    @Autowired
    public CategoryService(CategoryRepository gameCategoryRepository) {
        this.gameCategoryRepository = gameCategoryRepository;
    }

    public void seedGameCategories() {
        seedGameCategory("Action");
        seedGameCategory("Horror");
        seedGameCategory("shooter");
        seedGameCategory("family ");
    }

    private void seedGameCategory(String nameCategory) {
        Category gameCategory = gameCategoryRepository.findByName(nameCategory);
        if (gameCategory == null) {
            gameCategory = new Category();
            gameCategory.setName(nameCategory);
            gameCategoryRepository.save(gameCategory);
        }
    }


    public List<Category> getAllCategories() {
        return (List<Category>) gameCategoryRepository.findAll();
    }

    public Category createGameCategory(@RequestBody String input) {
        List<Category> gameCategories = getAllCategories();
        Category lastElem = gameCategories.get(gameCategories.size() - 1);
        Category newGameCategory = new Category(input);
        gameCategoryRepository.save(newGameCategory);
        return newGameCategory;
    }

    public ResultDTO updateGameCategory(@RequestBody Category gameCategory) {
        gameCategoryRepository.save(gameCategory);
        return new ResultDTO().setType("success").setMessage("Game category updated.");
    }

    public ResultDTO deleteGameCategory(@PathVariable("id") Long id) {
        Optional<Category> currentGameCategory = gameCategoryRepository.findById(id);
        if (currentGameCategory.isPresent()) {
            gameCategoryRepository.deleteById(id);
            return new ResultDTO().setType("success").setMessage("Game category deleted.");
        }
        return new ResultDTO().setType("fail").setMessage("Game category does not exists");
    }
}

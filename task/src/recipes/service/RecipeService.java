package recipes.service;

import org.springframework.stereotype.Service;
import recipes.domain.Recipe;
import recipes.repository.RecipeRepository;

import java.util.List;

/**
 * @author dzahbarov
 */

@Service
public class RecipeService {
    RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe findById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public boolean existById(int id) {
        return recipeRepository.existsById(id);
    }

    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}


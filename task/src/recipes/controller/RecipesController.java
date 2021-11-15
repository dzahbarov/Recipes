package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.Recipe;
import recipes.domain.User;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author dzahbarov
 */

@RestController
@RequestMapping("/api/recipe")
public class RecipesController {
    private final RecipeService recipeService;
    private final UserService userService;

    public RecipesController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping("search")
    public List<Recipe> searchRecipe(@RequestParam Map<String, String> vars) {
        if (vars.size() != 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (vars.containsKey("category")) {
            return recipeService.findByCategory(vars.get("category"));
        } else if (vars.containsKey("name")) {
            return recipeService.findByName(vars.get("name"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("{id}")
    public Recipe getRecipe(@PathVariable int id) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return recipe;
    }

    @PostMapping("new")
    public Map<String, Integer> postRecipe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Recipe recipe) {
        User user = userService.findByEmail(userDetails.getUsername());
        recipe.setAuthor(user);
        return Map.of("id", recipeService.save(recipe).getId());
    }

    @DeleteMapping("{id}")
    public void deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable int id) {
        validateAuth(recipeService, id, userDetails);
        recipeService.deleteById(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public void putRecipe(@AuthenticationPrincipal UserDetails userDetails,
                          @PathVariable int id, @Valid @RequestBody Recipe recipe) {
        validateAuth(recipeService, id, userDetails);
        User user = userService.findByEmail(userDetails.getUsername());
        recipe.setId(id);
        recipe.setAuthor(user);
        recipeService.save(recipe);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    private void validateAuth(RecipeService recipeService, int id, UserDetails userDetails) {
        Recipe recipe = recipeService.findById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!userDetails.getUsername().equals(recipe.getAuthor().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}

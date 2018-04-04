package com.example.recipe.services;

import com.example.recipe.commands.IngredientCommand;
import com.example.recipe.model.Ingredient;
import com.example.recipe.model.Recipe;
import com.example.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> optionalRecipe=recipeRepository.findById(ingredientCommand.getRecipeId());
        if(!optionalRecipe.isPresent()){
            log.error("recipe not found for id:"+ ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }
        else
        {
            Recipe recipe=optionalRecipe.get();
            Optional<Ingredient> optionalIngredient=recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
            
        }

        return null;
    }
}

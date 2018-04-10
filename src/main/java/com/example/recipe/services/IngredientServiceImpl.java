package com.example.recipe.services;

import com.example.recipe.commands.IngredientCommand;
import com.example.recipe.converters.IngredientCommandToIngredient;
import com.example.recipe.converters.IngredientToIngredientCommand;
import com.example.recipe.model.Ingredient;
import com.example.recipe.model.Recipe;
import com.example.recipe.model.UnitOfMeasure;
import com.example.recipe.repositories.RecipeRepository;
import com.example.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe=recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()){
            log.error("Recipe id not found id:"+ recipeId);
        }
        Recipe recipe=optionalRecipe.get();
        Optional<IngredientCommand> ingredientCommand=recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if(!ingredientCommand.isPresent()){
            log.error("Ingredient id not found id:"+ingredientId);
        }

        return ingredientCommand.get();

    }

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

            if(optionalIngredient.isPresent()){
                Ingredient ingredientFound=optionalIngredient.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(()-> new RuntimeException("UOM NOT FOUND")));
            }
            else
            {
                Ingredient ingredient=ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }

            Recipe savedRecipe=recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional=savedRecipe.getIngredients().stream().filter(recipeIngredients->recipeIngredients.getId().equals(ingredientCommand.getId())).findFirst();

            if(!savedIngredientOptional.isPresent())
            {
                savedIngredientOptional=savedRecipe.getIngredients().stream().filter(recipeIngredients->recipeIngredients.getDescription().equals(ingredientCommand.getDescription())).filter(recipeIngredients->recipeIngredients.getAmount().equals(ingredientCommand.getAmount())).filter(recipeIngredients->recipeIngredients.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId())).findFirst();
            }


            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }


    }
}

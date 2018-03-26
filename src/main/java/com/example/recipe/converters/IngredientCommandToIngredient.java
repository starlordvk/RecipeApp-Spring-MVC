package com.example.recipe.converters;

import com.example.recipe.commands.IngredientCommand;
import com.example.recipe.model.Ingredient;
import com.example.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand,Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand ingredientCommand) {
        if(ingredientCommand==null) {
            return null;
        }
        final Ingredient ingredient=new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        if(ingredientCommand.getRecipeId()!=null){
            Recipe recipe=new Recipe();
            recipe.setId(ingredientCommand.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
        ingredient.setAmount(ingredientCommand.getAmount());
        ingredient.setDescription(ingredientCommand.getDescription());

        ingredient.setUnitOfMeasure(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUnitOfMeasure()));
        return ingredient;
    }
}

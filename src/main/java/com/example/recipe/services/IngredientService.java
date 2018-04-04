package com.example.recipe.services;

import com.example.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}

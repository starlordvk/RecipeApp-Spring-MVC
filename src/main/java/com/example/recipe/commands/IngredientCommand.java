package com.example.recipe.commands;

import com.example.recipe.model.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String Description;
    private BigDecimal amount;
    private RecipeCommand recipe;
    private UnitOfMeasureCommand unitOfMeasure;
}

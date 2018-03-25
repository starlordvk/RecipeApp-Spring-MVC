package com.example.recipe.commands;


import com.example.recipe.model.Category;
import com.example.recipe.model.Difficulty;
import com.example.recipe.model.Ingredient;
import com.example.recipe.model.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<CategoryCommand> categories=new HashSet<>();
    private Difficulty difficulty;
    private Set<IngredientCommand> ingredients=new HashSet<>();
    private Byte[] image;
    private NotesCommand notes;

}

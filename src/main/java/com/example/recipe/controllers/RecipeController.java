package com.example.recipe.controllers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.model.Recipe;
import com.example.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @RequestMapping("/recipe/show/{id}")
    public String showRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipes(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeForm";

    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand=recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/show/"+savedCommand.getId();
    }

}

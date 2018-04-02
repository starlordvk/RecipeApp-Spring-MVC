package com.example.recipe.controllers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Service
@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipes(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return "recipe/recipeForm";

    }

    @PostMapping
    @RequestMapping("recipe/")
    public String saveUpdate(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand=recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }


    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model){
        log.debug("Deleting id: "+id);
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }

}

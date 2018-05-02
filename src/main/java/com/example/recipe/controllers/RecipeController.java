package com.example.recipe.controllers;

import com.example.recipe.commands.RecipeCommand;
import com.example.recipe.exceptions.NotFoundException;
import com.example.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Slf4j
@Service
@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));

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
    public String saveUpdate(@Valid @ModelAttribute("recipe")  RecipeCommand recipeCommand, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "recipe/recipeform";
        }
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView numberFormat(Exception exception){
        log.error("handling number format exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }


}

package com.example.recipe.controllers;

import com.example.recipe.model.Category;
import com.example.recipe.model.UnitOfMeasure;
import com.example.recipe.repositories.CategoryRepository;
import com.example.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        Optional<Category> category=categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasure=unitOfMeasureRepository.findByUom("Teaspoon");
        System.out.println("Category ID is "+category.get().getId());
        System.out.println("UnitOfMeasure ID is "+unitOfMeasure .get().getId());
        return "Index";
    }
}

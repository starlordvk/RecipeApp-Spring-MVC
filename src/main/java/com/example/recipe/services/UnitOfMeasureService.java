package com.example.recipe.services;

import com.example.recipe.commands.UnitOfMeasureCommand;
import com.example.recipe.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}

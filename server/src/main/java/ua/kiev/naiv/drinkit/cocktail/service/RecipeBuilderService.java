package ua.kiev.naiv.drinkit.cocktail.service;

import ua.kiev.naiv.drinkit.cocktail.model.Recipe;
import ua.kiev.naiv.drinkit.cocktail.model.RecipeBuilder;

/**
 * Created by bstorozhuk on 27.05.14.
 */
public interface RecipeBuilderService {
    RecipeBuilder getRecipeBuilder();

    Recipe buildAndSave(RecipeBuilder recipeBuilder);
}

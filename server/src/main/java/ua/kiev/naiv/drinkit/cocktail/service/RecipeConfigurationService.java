package ua.kiev.naiv.drinkit.cocktail.service;

import ua.kiev.naiv.drinkit.cocktail.model.Recipe;
import ua.kiev.naiv.drinkit.cocktail.model.RecipeConfigurationPOJO;

/**
 * Created by bstorozhuk on 27.05.14.
 */
public interface RecipeConfigurationService {
    RecipeConfigurationPOJO getRecipeConfiguration();

    Recipe configureRecipe(RecipeConfigurationPOJO recipeConfiguration);
}

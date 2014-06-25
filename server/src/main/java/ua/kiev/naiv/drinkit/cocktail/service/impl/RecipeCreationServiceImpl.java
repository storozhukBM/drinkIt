package ua.kiev.naiv.drinkit.cocktail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.naiv.drinkit.cocktail.model.*;
import ua.kiev.naiv.drinkit.cocktail.repository.*;
import ua.kiev.naiv.drinkit.cocktail.service.RecipeConfigurationService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeCreationServiceImpl implements RecipeConfigurationService {

    @Resource
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Resource
    OptionRepository optionRepository;

    @Resource
    CocktailTypeRepository cocktailTypeRepository;

    @Resource
    IngredientWithQuantityRepository ingredientWithQuantityRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCreationServiceImpl.class);

    @Override
    public RecipeConfigurationPOJO getRecipeConfiguration() {
        return new RecipeConfigurationPOJO();
    }

    @Override
    public Recipe configureRecipe(RecipeConfigurationPOJO recipeConfiguration) {

        Integer[] ingredients = recipeConfiguration.getIngredients();
        Double[] quantities = recipeConfiguration.getQuantities();
        Integer[] options = recipeConfiguration.getOptions();
        String name = recipeConfiguration.getName();
        String description = recipeConfiguration.getDescription();
        Integer cocktailType = recipeConfiguration.getCocktailType();
        MultipartFile image = recipeConfiguration.getImage();
        MultipartFile thumbnail = recipeConfiguration.getImage();

        Recipe recipe = new Recipe();
        Set<IngredientWithQuantity> ingredientWithQuantitySet = new HashSet<>();

        for (int i=0; i < ingredients.length; i++) {
            IngredientWithQuantity ingredientWithQuantity = new IngredientWithQuantity();

            CocktailIngredientId cocktailIngredientId = new CocktailIngredientId();
            cocktailIngredientId.setRecipe(recipe);
            cocktailIngredientId.setIngredient(ingredientRepository.findOne(ingredients[i]));

            ingredientWithQuantity.setCocktailIngredientId(cocktailIngredientId);
            ingredientWithQuantity.setIngredient(ingredientRepository.findOne(ingredients[i]));
            ingredientWithQuantity.setQuantity(quantities[i].intValue());

            ingredientWithQuantitySet.add(ingredientWithQuantity);
        }

        Set<Option> optionsSet = new HashSet<>();
        for (Integer optionId: options) {
            optionsSet.add(optionRepository.findOne(optionId));
        }

        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setCocktailType(cocktailTypeRepository.findOne(cocktailType));
        recipe.setIngredientsWithQuantities(ingredientWithQuantitySet);
        recipe.setOptions(optionsSet);

        try {
            recipe.setImage(image.getBytes());
            recipe.setThumbnail(thumbnail.getBytes());
        } catch (IOException e) {
            RecipeCreationServiceImpl.LOGGER.error("Can't get bytes from image.", e);
        }

        LOGGER.info("Recipe configured: " + recipe.toString());

        return recipe;
    }

}

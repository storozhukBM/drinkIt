package ua.kiev.naiv.drinkit.cocktail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.kiev.naiv.drinkit.cocktail.model.*;
import ua.kiev.naiv.drinkit.cocktail.repository.*;
import ua.kiev.naiv.drinkit.cocktail.model.RecipeBuilder;
import ua.kiev.naiv.drinkit.cocktail.service.RecipeBuilderService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeBuilderServiceImpl implements RecipeBuilderService {

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

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeBuilderServiceImpl.class);

    @Override
    public RecipeBuilder getRecipeBuilder() {
        return new RecipeBuilderImpl();
    }

    @Override
    @Transactional
    public Recipe buildAndSave(RecipeBuilder recipeBuilder) {

        Integer[] ingredients = recipeBuilder.getIngredients();
        Double[] quantities = recipeBuilder.getQuantities();
        Integer[] options = recipeBuilder.getOptions();
        String name = recipeBuilder.getName();
        String description = recipeBuilder.getDescription();
        Integer cocktailType = recipeBuilder.getCocktailType();
        MultipartFile image = recipeBuilder.getImage();
        MultipartFile thumbnail = recipeBuilder.getImage();

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

        try {
            recipe.setName(name);
            recipe.setDescription(description);
            recipe.setCocktailType(cocktailTypeRepository.findOne(cocktailType));
            recipe.setIngredientsWithQuantities(ingredientWithQuantitySet);
            recipe.setOptions(optionsSet);
            recipe.setImage(image.getBytes());
            recipe.setThumbnail(thumbnail.getBytes());
            recipe = recipeRepository.save(recipe);
            LOGGER.info("Recipe saved to DB: " + recipe.toString());

        } catch (IOException e) {
            RecipeBuilderServiceImpl.LOGGER.error("Can't get bytes from image.", e);
        }
        return recipe;
    }

}

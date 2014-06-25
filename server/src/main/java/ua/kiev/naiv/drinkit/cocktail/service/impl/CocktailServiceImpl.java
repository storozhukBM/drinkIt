package ua.kiev.naiv.drinkit.cocktail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.naiv.drinkit.cocktail.model.CocktailType;
import ua.kiev.naiv.drinkit.cocktail.model.Ingredient;
import ua.kiev.naiv.drinkit.cocktail.model.Option;
import ua.kiev.naiv.drinkit.cocktail.model.Recipe;
import ua.kiev.naiv.drinkit.cocktail.repository.*;
import ua.kiev.naiv.drinkit.cocktail.search.Criteria;
import ua.kiev.naiv.drinkit.cocktail.search.RecipeComparatorByCriteria;
import ua.kiev.naiv.drinkit.cocktail.search.SearchSpecification;
import ua.kiev.naiv.drinkit.cocktail.service.CocktailService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel Kolmykov
 * Date: 20.07.13
 * Time: 21:56
 */
@Service
public class CocktailServiceImpl implements CocktailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CocktailService.class);

    @Resource
    RecipeRepository recipeRepository;

    @Resource
    CocktailTypeRepository cocktailTypeRepository;

    @Resource
    OptionRepository optionRepository;

    @Resource
    IngredientWithQuantityRepository ingredientWithQuantityRepository;

    @Override
    @Transactional
    public Recipe create(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        LOGGER.info("Recipe saved: " + recipe.toString());
        return savedRecipe;
    }

    @Override
    public Recipe delete(int id) {
        throw new IllegalStateException("Not implemented yet"); //TODO Not implemented
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findByCriteria(Criteria criteria) {
        List<Recipe> recipes = recipeRepository.findAll(SearchSpecification.byCriteria(criteria));
        if (criteria.getIngredients().size() > 0) {
            Collections.sort(recipes, new RecipeComparatorByCriteria(criteria));
        }
        LOGGER.info("findByCriteria({}): found {} records", criteria, recipes.size());
        return recipes;
    }

    @Override
    public Recipe update(Recipe recipe) {
        throw new IllegalStateException("Not implemented yet"); //TODO Not implemented
    }

    @Override
    public Recipe getById(int id) {
        return recipeRepository.findOne(id);
    }

//    @Override
//    public IngredientWithQuantity create(IngredientWithQuantity ingredientWithQuantity) {
//        ingredientWithQuantityRepository.save(ingredientWithQuantity);
//        return ingredientWithQuantityRepository.findOne();
//    }

    @Override
    public CocktailType findCocktailTypeById(int id) {
        return cocktailTypeRepository.findOne(id);
    }

//    @Override
//    public List<CocktailType> findAllCocktailType() {
//        return cocktailTypeRepository.findAll();
//    }

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        LOGGER.info("getIngredients: found {} records", ingredients.size());
        return ingredients;
    }

    @Override
    public List<Option> getOptions() {
        List<Option> options = optionRepository.findAll();
        LOGGER.info("getOptions: find {} records", options.size());
        return options;
    }

    @Override
    public List<CocktailType> getCocktailTypes() {
        List<CocktailType> cocktailTypes = cocktailTypeRepository.findAll();
        LOGGER.info("getCocktailTypes: find {} records", cocktailTypes.size());
        return cocktailTypes;
    }


    @Override
    public Ingredient findIngredientById(int id) {
        return ingredientRepository.findOne(id);
    }

//    @Override
//    public List<CocktailType> getCocktailTypes() {
//        List<CocktailType> cocktailTypes = cocktailTypeRepository.findAll();
//        LOGGER.info("getCocktailTypes: found {} records", cocktailTypes.size());
//        return cocktailTypes;
//    }

    @Override
    public Option findOptionById(Integer optionId) {
        return optionRepository.findOne(optionId);
    }
}

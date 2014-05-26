package ua.kiev.naiv.drinkit.cocktail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.naiv.drinkit.cocktail.common.JsonMixin;
import ua.kiev.naiv.drinkit.cocktail.mixin.RecipeInfoResult;
import ua.kiev.naiv.drinkit.cocktail.mixin.RecipeSearchResult;
import ua.kiev.naiv.drinkit.cocktail.model.*;
import ua.kiev.naiv.drinkit.cocktail.search.Criteria;
import ua.kiev.naiv.drinkit.cocktail.service.CocktailService;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel Kolmykov
 * Date: 21.07.13
 * Time: 13:54
 */
@Controller
@RequestMapping(value = "cocktail")
public class CocktailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CocktailController.class);

	@Autowired
	CocktailService cocktailService;

    @RequestMapping(value = "/add-recipe", method = RequestMethod.GET)
    public ModelAndView recipePage() {
        ModelAndView modelAndView = new ModelAndView("recipe-page");
        modelAndView.addObject("recipeBuilder", cocktailService.getRecipeBuilder());
        modelAndView.addObject("options", cocktailService.getOptions());
        modelAndView.addObject("ingredients", cocktailService.getIngredients());
        modelAndView.addObject("cocktailTypes", cocktailService.getCocktailTypes());
        return modelAndView;
    }

    @RequestMapping(value = "/add-recipe", method = RequestMethod.POST)
    public void addRecipe(
                          @RequestParam("image") MultipartFile image,
                          @RequestParam("image") MultipartFile thumbnail,
                          @RequestParam("name") String name,
                          @RequestParam("description") String description,
                          @RequestParam("cocktailType") Integer cocktailTypeId,
                          @RequestParam("options") Integer[] options,
                          @RequestParam("ingredients") Integer[] ingredients,
                          @RequestParam("quantities") Double[] quantities
    ) {
        Recipe.RecipeBuilder builder = cocktailService.getRecipeBuilder();

        Set<IngredientWithQuantity> ingredientWithQuantitySet = new HashSet<>();

        for (int i=0; i < ingredients.length; i++) {
            IngredientWithQuantity ingredientWithQuantity = new IngredientWithQuantity();
            ingredientWithQuantity.setCocktailIngredientId(new CocktailIngredientId());
            ingredientWithQuantity.setIngredient(cocktailService.findIngredientById(ingredients[i]));
            ingredientWithQuantity.setQuantity(quantities[i].intValue());

            ingredientWithQuantitySet.add(ingredientWithQuantity);
        }

        Set<Option> optionsSet = new HashSet<>();
        for (Integer optionId: options) {
            optionsSet.add(cocktailService.findOptionById(optionId));
        }
        try {

            Recipe recipe = builder.build(
                    name,
                    description,
                    cocktailService.findCocktailTypeById(cocktailTypeId),
                    ingredientWithQuantitySet,
                    optionsSet,
                    image.getBytes(),
                    thumbnail.getBytes()
            );

            cocktailService.create(recipe);
        } catch (IOException e) {
            LOGGER.error("Can't get bytes from image.", e);
        }
    }

//    @RequestMapping(value = "/add-ingredient-with-quantity", method = RequestMethod.GET)
//    public ModelAndView addIngredientWithQuantity(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            @RequestParam("ingredient") Integer ingredient,
//            @RequestParam("quantity") Integer quantity
//            ) {
//
//    }

	@RequestMapping("/getById")
	@ResponseBody
	@JsonMixin(RecipeInfoResult.class)
    @Transactional(readOnly = true)
	public Recipe getById(@RequestParam int id) {
		return cocktailService.getById(id);
	}

	@RequestMapping("/getIngredients")
	@ResponseBody
	public List<Ingredient> getIngredients() {
		return cocktailService.getIngredients();
	}

	@RequestMapping("/search")
	@ResponseBody
	@JsonMixin(value = RecipeSearchResult.class, targetClass = Recipe.class)
    @Transactional(readOnly = true)
	public List<Recipe> searchRecipes(@RequestParam(value = "criteria") String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		Criteria criteria = null;
		try {
			criteria = objectMapper.readValue(json, Criteria.class);
		} catch (IOException e) {
			LOGGER.error("Bad criteria", e);
			return null;
		}

		return cocktailService.findByCriteria(criteria);
	}

}

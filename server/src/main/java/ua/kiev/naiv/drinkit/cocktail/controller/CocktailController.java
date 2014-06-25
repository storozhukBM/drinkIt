package ua.kiev.naiv.drinkit.cocktail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.kiev.naiv.drinkit.cocktail.common.JsonMixin;
import ua.kiev.naiv.drinkit.cocktail.mixin.RecipeInfoResult;
import ua.kiev.naiv.drinkit.cocktail.mixin.RecipeSearchResult;
import ua.kiev.naiv.drinkit.cocktail.model.Ingredient;
import ua.kiev.naiv.drinkit.cocktail.model.Recipe;
import ua.kiev.naiv.drinkit.cocktail.model.RecipeConfigurationPOJO;
import ua.kiev.naiv.drinkit.cocktail.search.Criteria;
import ua.kiev.naiv.drinkit.cocktail.service.CocktailService;
import ua.kiev.naiv.drinkit.cocktail.service.RecipeConfigurationService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    MessageSource messageSource;

    @Autowired
    RecipeConfigurationService recipeConfigurationService;

    @RequestMapping(value = "/add-recipe", method = RequestMethod.GET)
    public ModelAndView recipePage() {
        ModelAndView modelAndView = new ModelAndView("recipe-page");
        modelAndView.addObject("command", recipeConfigurationService.getRecipeConfiguration());
        modelAndView.addObject("options", cocktailService.getOptions());
        modelAndView.addObject("ingredients", cocktailService.getIngredients());
        modelAndView.addObject("cocktailTypes", cocktailService.getCocktailTypes());
        return modelAndView;
    }

    @RequestMapping(value = "/add-recipe", method = RequestMethod.POST)
    public String addRecipe(
            @Valid @ModelAttribute(value = "command") RecipeConfigurationPOJO recipeConfiguration,
            BindingResult result,
            Model model
    ) {
        model.addAttribute("options", cocktailService.getOptions());
        model.addAttribute("ingredients", cocktailService.getIngredients());
        model.addAttribute("cocktailTypes", cocktailService.getCocktailTypes());

        if (result.hasErrors()) {
            return "recipe-page";
        }

        Recipe configuredRecipe = recipeConfigurationService.configureRecipe(recipeConfiguration);
        cocktailService.create(configuredRecipe);

        String successMessage = messageSource.getMessage("CocktailController.adding.success",
                null, Locale.getDefault());
        model.addAttribute("successMessage", successMessage);

        model.addAttribute("command", recipeConfigurationService.getRecipeConfiguration());

        return "recipe-page";
    }

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

package ua.kiev.naiv.drinkit.cocktail.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bstorozhuk on 27.05.14.
 */
public interface RecipeBuilder {

    Integer[] getIngredients();

    Double[] getQuantities();

    Integer[] getOptions();

    String getName();

    String getDescription();

    Integer getCocktailType();

    MultipartFile getImage();
}

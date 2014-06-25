package ua.kiev.naiv.drinkit.cocktail.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* Created by bstorozhuk on 27.05.14.
*/

@Embeddable
public class RecipeConfigurationPOJO {

    @NotNull
    @Size(max=30)
    @NotBlank
    String name;
    @NotNull
    @Size(max=5000)
    @NotBlank
    String description;
    @NotNull
    Integer cocktailType;
    @NotNull
    @NotEmpty
    Integer[] ingredients;
    @NotNull
    @NotEmpty
    Double[] quantities;
    @NotNull
    Integer[] options;
    @NotNull
    MultipartFile image;
    @NotNull
    MultipartFile thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCocktailType() {
        return cocktailType;
    }

    public void setCocktailType(Integer cocktailType) {
        this.cocktailType = cocktailType;
    }

    public Double[] getQuantities() {
        return quantities;
    }

    public void setQuantities(Double[] quantities) {
        this.quantities = quantities;
    }

    public Integer[] getOptions() {
        return options;
    }

    public void setOptions(Integer[] options) {
        this.options = options;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Integer[] ingredients) {
        this.ingredients = ingredients;
    }


}

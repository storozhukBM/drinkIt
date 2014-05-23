package ua.kiev.naiv.drinkit.cocktail.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel Kolmykov
 * Date: 20.07.13
 * Time: 16:42
 */

@Entity
@Table(name = "recipes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
        @UniqueConstraint(columnNames = "name")
})
public class Recipe implements Serializable {

    private Integer id;
    private String name;
    private String description;
    @JsonProperty("cocktailTypeId")
    @JsonIdentityReference(alwaysAsId = true)
    private CocktailType cocktailType;
    private Set<IngredientWithQuantity> ingredientsWithQuantities;
    @JsonProperty("optionIds")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Option> options;
    private byte[] image;
    private byte[] thumbnail;

    @Transient
    public Set<Integer> getIngredientIds() {
        Set<Integer> result = new HashSet<>();
        for (IngredientWithQuantity ingredientWithQuantity : getIngredientsWithQuantities()) {
            result.add(ingredientWithQuantity.getIngredient().getId());
        }
        return result;
    }

    @Id
    @GeneratedValue()
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne()
    @JoinColumn(name = "type_id")
    public CocktailType getCocktailType() {
        return cocktailType;
    }

    public void setCocktailType(CocktailType cocktailType) {
        this.cocktailType = cocktailType;
    }

    @OneToMany(mappedBy = "cocktailIngredientId.recipe")
    public Set<IngredientWithQuantity> getIngredientsWithQuantities() {
        return ingredientsWithQuantities;
    }

    public void setIngredientsWithQuantities(Set<IngredientWithQuantity> ingredientsWithQuantities) {
        this.ingredientsWithQuantities = ingredientsWithQuantities;
    }

    @ManyToMany()
    @JoinTable(name = "recipes_has_options",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    @Column(name = "image")
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Column()
    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static RecipeBuilder getBuilder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder {

        Recipe built = new Recipe();
        String name;
        String description;
        CocktailType cocktailType;
        String[] ingredients;
        Integer[] quantities;
        Set<Option> options;
        byte[] image;
        byte[] thumbnail;

        public Integer getId() {
            return built.getId();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CocktailType getCocktailType() {
            return cocktailType;
        }

        public void setCocktailType(CocktailType cocktailType) {
            this.cocktailType = cocktailType;
        }

        public Integer[] getQuantities() {
            return quantities;
        }

        public void setQuantities(Integer[] quantities) {
            this.quantities = quantities;
        }

        public Set<Option> getOptions() {
            return options;
        }

        public void setOptions(Set<Option> options) {
            this.options = options;
        }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }

        public byte[] getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(byte[] thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String[] getIngredients() {
            return ingredients;
        }

        public void setIngredients(String[] ingredients) {
            this.ingredients = ingredients;
        }

        public Recipe build(
                String name,
                String description,
                CocktailType cocktailType,
                Set<IngredientWithQuantity> ingredientsWithQuantities,
                Set<Option> options,
                byte[] image,
                byte[] thumbnail
        ) {
            built.setName(name);
            built.setDescription(description);
            built.setCocktailType(cocktailType);
            built.setIngredientsWithQuantities(ingredientsWithQuantities);
            built.setOptions(options);
            built.setImage(image);
            built.setThumbnail(thumbnail);
            return built;
        }
    }
}
package ua.kiev.naiv.drinkit.cocktail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.naiv.drinkit.cocktail.model.IngredientWithQuantity;

/**
 * Created by bstorozhuk on 23.05.14.
 */
public interface IngredientWithQuantityRepository extends JpaRepository<IngredientWithQuantity, Integer> {
}

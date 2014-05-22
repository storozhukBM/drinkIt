package ua.kiev.naiv.drinkit.cocktail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.kiev.naiv.drinkit.cocktail.model.Option;

/**
 * Created by bstorozhuk on 22.05.14.
 */
public interface OptionRepository extends JpaRepository<Option, Integer>, JpaSpecificationExecutor<Option> {
}
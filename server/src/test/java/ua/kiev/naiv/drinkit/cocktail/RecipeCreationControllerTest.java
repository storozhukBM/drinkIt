package ua.kiev.naiv.drinkit.cocktail;

import junit.framework.TestCase;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.kiev.naiv.drinkit.cocktail.model.RecipeConfigurationPOJO;
import ua.kiev.naiv.drinkit.cocktail.service.CocktailService;
import ua.kiev.naiv.drinkit.springconfig.AppConfig;
import ua.kiev.naiv.drinkit.springconfig.WebConfig;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
public class RecipeCreationControllerTest extends TestCase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CocktailService cocktailService;

    private MockMvc mockMvc;

    String cockTailType;
    String[] options;
    String[] ingredients;
    String[] quantities;
    BaseMatcher listInitialized;


    @Before
    public void init() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        cockTailType = cocktailService.getCocktailTypes().get(0).getId().toString();

        options = new String[] {String.valueOf(cocktailService.getOptions().get(0).getId()),
                String.valueOf(cocktailService.getOptions().get(1).getId())};

        ingredients = new String[] {String.valueOf(cocktailService.getIngredients().get(0).getId()),
                String.valueOf(cocktailService.getIngredients().get(1).getId())};

        quantities = new String[] {"10.1", "9.9"};

        listInitialized = new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object item) {
                List ingredients = (List) item;
                return !ingredients.isEmpty();
            }

            @Override
            public void describeTo(Description description) {}
        };
    }

    @Test
    public void testAddRecipeFormPositive() throws Exception {

        ResultActions ra =  mockMvc.perform(fileUpload("/cocktail/add-recipe.html")
                        .file(new MockMultipartFile("image", "testimage.png", "text/plain", "image".getBytes()))
                        .file(new MockMultipartFile("thumbnail", "testimage.png", "text/plain", "thumbnail".getBytes()))
                        .param("name", "name")
                        .param("description", "desc")
                        .param("cocktailType", cockTailType)
                        .param("options", options)
                        .param("ingredients", ingredients)
                        .param("quantities", quantities)
        );
        ra.andExpect(status().isOk())
                .andExpect(view().name("recipe-page"))

                .andExpect(model().attribute("successMessage", new BaseMatcher<Object>() {
                    @Override
                    public boolean matches(Object item) {
                        return item.toString().equals("Your recipe successfully added.");
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                }))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void testAddRecipeFormNegative() throws Exception {

        ResultActions ra =  mockMvc.perform(fileUpload("/cocktail/add-recipe.html"));
        ra.andExpect(status().isOk());
        ra.andExpect(view().name("recipe-page"));
        ra.andExpect(model().hasErrors());
        ra.andExpect(model().errorCount(12));
        ra.andExpect(model().attributeHasFieldErrors("command",
                "name", "description", "cocktailType", "options",
                "ingredients", "quantities", "image", "thumbnail"
        ));
    }

    @Test
    public void testAddRecipePage() throws Exception {
        ResultActions ra =  mockMvc.perform(get("/cocktail/add-recipe.html"));

        ra.andExpect(status().isOk());
        ra.andExpect(view().name("recipe-page"));
        ra.andExpect(model().attribute("ingredients", listInitialized));
        ra.andExpect(model().attribute("options", listInitialized));
        ra.andExpect(model().attribute("cocktailTypes", listInitialized));

    }

    @Test
    public void testAddRecipeFormDataSavedAfterValidation() throws Exception {
        String name = "name";

        ResultActions ra =  mockMvc.perform(fileUpload("/cocktail/add-recipe.html")
                        .file(new MockMultipartFile("image", "testimage.png", "text/plain", "image".getBytes()))
                        .file(new MockMultipartFile("thumbnail", "testimage.png", "text/plain", "thumbnail".getBytes()))
                        .param("name", name)
                        .param("cocktailType", cockTailType)
                        .param("options", options)
                        .param("ingredients", ingredients)
                        .param("quantities", quantities)
        );

        ra.andExpect(status().isOk());
        ra.andExpect(view().name("recipe-page"));
        ra.andExpect(model().attribute("ingredients", listInitialized));
        ra.andExpect(model().attribute("options", listInitialized));
        ra.andExpect(model().attribute("cocktailTypes", listInitialized));
        ra.andExpect(model().hasErrors());
        ra.andExpect(model().attributeHasFieldErrors("command", "description"));
        ra.andExpect(model().attribute("command", new BaseMatcher<RecipeConfigurationPOJO>() {
            @Override
            public boolean matches(Object item) {
                RecipeConfigurationPOJO testRecipe = (RecipeConfigurationPOJO) item;
                return
                        arraysEqual(testRecipe.getIngredients(), ingredients) &&
                        arraysEqual(testRecipe.getQuantities(), quantities) &&
                        arraysEqual(testRecipe.getOptions(), options) &&
                        testRecipe.getName().equals("name") &&
                        testRecipe.getCocktailType().toString().equals(cockTailType) &&
                        testRecipe.getDescription() == null;
            }

            @Override
            public void describeTo(Description description) {

            }

            public boolean arraysEqual(Object[] first, Object[] second) {
                return Arrays.toString(first).equals(Arrays.toString(second));
            }
        }));

    }

}

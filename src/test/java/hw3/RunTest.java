package hw3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public class RunTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("Recipe search query")
    void getRecipeSearchQueryTest() throws IOException {
        given()
                .when()
                .get(getURL() + "/recipes/complexSearch?query=burger&apiKey=" + getApiKey())
                                .then()
                                .statusCode(200);
    }

    @Test
    void getRecipeWithQueryParametersPositiveTest() throws IOException {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .when()
                .get(getURL() + "/recipes/716429/information")
                .then()
                .statusCode(200);
    }

    @Test
    void addMealTest() throws IOException {
        String id = given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"date\": 1644881179,\n"
                        + " \"slot\": 1,\n"
                        + " \"position\": 0,\n"
                        + " \"type\": \"INGREDIENTS\",\n"
                        + " \"value\": {\n"
                        + " \"ingredients\": [\n"
                        + " {\n"
                        + " \"name\": \"1 banana\"\n"
                        + " }\n"
                        + " ]\n"
                        + " }\n"
                        + "}")
                .when()
                .post(getURL() + "/mealplanner/geekbrains/items")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

        given()
                .queryParam("hash", "a3da66460bfb7e62ea1c96cfa0b7a634a346ccbf")
                .queryParam("apiKey", getApiKey())
                .delete(getURL() + "/mealplanner/geekbrains/items/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void getRecipeWithBodyChecksAfterRequestPositiveTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .when()
                .get(getURL() + "/recipes/716429/information")
                .body()
                .jsonPath();
        assertThat(response.get("vegetarian"), is(false));
        assertThat(response.get("vegan"), is(false));
        assertThat(response.get("license"), equalTo("CC BY-SA 3.0"));
        assertThat(response.get("pricePerServing"), equalTo(163.15F));
        assertThat(response.get("extendedIngredients[0].aisle"), equalTo("Milk, Eggs, Other Dairy"));
    }

    @Test
    void getRecipeWithBodyChecksInGivenPositiveTest() throws IOException {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeNutrition", "false")
                .expect()
                .body("vegetarian", is(false))
                .body("vegan", is(false))
                .body("license", equalTo("CC BY-SA 3.0"))
                .body("pricePerServing", equalTo(163.15F))
                .body("extendedIngredients[0].aisle", equalTo("Milk, Eggs, Other Dairy"))
                .when()
                .get(getURL() + "/recipes/716429/information");
    }
}

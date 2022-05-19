package hw4;

import hw4.dto.response.ComplexSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Backend Java. Homework 4
 *
 * @author Vitalii Luzhnov
 * @version 17.05.2022
 */
public class ComplexSearchTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("GET. Recipe search query")
    void getRecipeSearchQueryTest() throws IOException {
        ComplexSearchResponse response = given()
                .spec(requestSpecification)
                .queryParam("query", "burger")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(ComplexSearchResponse.class);

//        for (ComplexSearchResponse.Result str : response.getResults()) {
//            assertThat(str.getTitle(), containsString("Burger"));
//        }
        response.getResults().forEach(product ->
                assertThat(product.getTitle(), containsString("Burger")));
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search query unique recipe")
    void getSearchQueryUniqueRecipeTest() throws IOException {
        ComplexSearchResponse response = given()
                .spec(requestSpecification)
                .queryParam("query", "$50,000 Burger")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(ComplexSearchResponse.class);

//        for (ComplexSearchResponse.Result str : response.getResults()) {
//            assertThat(str.getTitle(), equalToIgnoringCase("$50,000 Burger"));
//        }
        response.getResults().forEach(product ->
                assertThat(product.getTitle(), equalToIgnoringCase("$50,000 Burger")));
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by cooking time")
    void getSearchByCookingTimeTest() throws IOException {
        ComplexSearchResponse response = given()
                .spec(requestSpecification)
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", 5)
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(ComplexSearchResponse.class);

//        for (ComplexSearchResponse.Result str : response.getResults()) {
//            assertThat(str.getReadyInMinutes(), lessThanOrEqualTo(5));
//        }
        response.getResults().forEach(product ->
                assertThat(product.getReadyInMinutes(), lessThanOrEqualTo(5)));
    }

    @Test
    @Tag("Negative")
    @DisplayName("GET. Search by cooking time is negative")
    void getSearchByCookingTimeisNegativeTest() throws IOException {
        given()
                .spec(requestSpecification)
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", "five")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .statusCode(404);
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by minimum calories")
    void getSearchByMinimumCaloriesTest() throws IOException {
        ComplexSearchResponse response = given()
                .spec(requestSpecification)
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "asc")
                .queryParam("minCalories", 100)
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(ComplexSearchResponse.class);

        int i = 0;
        float previousValue = 0f;
        for (ComplexSearchResponse.Result res : response.getResults()) {
            for (ComplexSearchResponse.Nutrient nutr : res.nutrition.getNutrients()) {
                assertThat(nutr.getName(), equalToIgnoringCase("Calories"));
                if (i == 0) {
                    assertThat(nutr.getAmount(), greaterThanOrEqualTo(100f));
                }
                if (i != 0) {
                    assertThat(nutr.getAmount() >= previousValue, is (true));//
                }
                previousValue = nutr.getAmount();
            }
            i++;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by maximum calories")
    void getSearchByMaximumCaloriesTest() throws IOException {
        ComplexSearchResponse response = given()
                .spec(requestSpecification)
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "desc")
                .queryParam("maxCalories", 800)
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(ComplexSearchResponse.class);

        int i = 0;
        float previousValue = 0f;
        for (ComplexSearchResponse.Result res : response.getResults()) {
            for (ComplexSearchResponse.Nutrient nutr : res.nutrition.getNutrients()) {
                assertThat(nutr.getName(), equalToIgnoringCase("Calories"));
                if (i == 0) {
                    assertThat(nutr.getAmount(), lessThanOrEqualTo(800f));
                }
                if (i != 0) {
                    assertThat(nutr.getAmount() <= previousValue, is (true));//
                }
                previousValue = nutr.getAmount();
            }
            i++;
        }
    }
}

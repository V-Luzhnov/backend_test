package hw4;

import io.restassured.path.json.JsonPath;
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
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("query", "burger")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].title"), containsString("Burger"));
            index--;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search query unique recipe")
    void getSearchQueryUniqueRecipeTest() throws IOException {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("query", "$50,000 Burger")
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].title"), equalToIgnoringCase("$50,000 Burger"));
            index--;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by cooking time")
    void getSearchByCookingTimeTest() throws IOException {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", 5)
                .when()
                .get(getURL() + "/recipes/complexSearch")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].readyInMinutes"), lessThanOrEqualTo(5));
            index--;
        }
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
        JsonPath response = given()
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
                .jsonPath();

        int maxIndex = getMaxIndex(response);
        int i = 0;
        while (i <= maxIndex) {
            assertThat(response.get("results[" + i + "].nutrition.nutrients[0].name"), equalToIgnoringCase("Calories"));
            if (i == 0) {
                assertThat(response.get("results[" + i + "].nutrition.nutrients[0].amount"), greaterThanOrEqualTo(100f));
            }
            if (i != maxIndex) {
            assertThat((Float) response.get("results[" + i + "].nutrition.nutrients[0].amount") <=
                    (Float) response.get("results[" + (i + 1) + "].nutrition.nutrients[0].amount"), is (true));
            }
            i++;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by maximum calories")
    void getSearchByMaximumCaloriesTest() throws IOException {
        JsonPath response = given()
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
                .jsonPath();

        int maxIndex = getMaxIndex(response);
        int i = 0;
        while (i <= maxIndex) {
            assertThat(response.get("results[" + i + "].nutrition.nutrients[0].name"), equalToIgnoringCase("Calories"));
            if (i == 0) {
                assertThat(response.get("results[" + i + "].nutrition.nutrients[0].amount"), lessThanOrEqualTo(800f));
            }
            if (i != maxIndex) {
                assertThat((Float) response.get("results[" + i + "].nutrition.nutrients[0].amount") >=
                        (Float) response.get("results[" + (i + 1) + "].nutrition.nutrients[0].amount"), is (true));
            }
            i++;
        }
    }
}

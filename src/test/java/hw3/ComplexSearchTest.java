package hw3;

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
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public class ComplexSearchTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("GET. Recipe search query")
    void getRecipeSearchQueryTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "burger")
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].title"), containsString("Burger"));
            index --;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search query unique recipe")
    void getSearchQueryUniqueRecipeTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "$50,000 Burger")
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].title"), equalToIgnoringCase("$50,000 Burger"));
            index --;
        }
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by cooking time")
    void getSearchByCookingTimeTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", 5)
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        int index = getMaxIndex(response);
        while (index >= 0) {
            assertThat(response.get("results[" + index + "].readyInMinutes"), lessThanOrEqualTo(5));
            index --;
        }
    }

    @Test
    @Tag("Negative")
    @DisplayName("GET. Search by cooking time is negative")
    void getSearchByCookingTimeisNegativeTest() throws IOException {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("addRecipeInformation", true)
                .queryParam("maxReadyTime", "five")
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    @Tag("Positive")
    @DisplayName("GET. Search by minimum calories")
    void getSearchByMinimumCaloriesTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "asc")
                .queryParam("minCalories", 100)
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(200)
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
                .queryParam("apiKey", getApiKey())
                .queryParam("sort", "calories")
                .queryParam("sortDirection", "desc")
                .queryParam("maxCalories", 800)
//                .log()
//                .all()
                .when()
                .get(getURL() + "/recipes/complexSearch")
//                .prettyPeek()
                .then()
                .statusCode(200)
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

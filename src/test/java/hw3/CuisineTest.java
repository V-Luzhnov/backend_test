package hw3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public class CuisineTest extends AbstractTest {

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

        int size = getSize(response);
        while (size >= 0) {
            assertThat(response.get("results[" + size + "].title").toString().contains("Burger"), is(true));
            size --;
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

        int size = getSize(response);
        while (size >= 0) {
            assertThat(response.get("results[" + size + "].title"), is("$50,000 Burger"));
            size --;
        }
    }
}

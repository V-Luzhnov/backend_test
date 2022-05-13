package hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public class AddToShoppingListTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("POST. Add to Shopping List")
    void addToShoppingListTest() throws IOException {
        String id = given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .body("{\n"
                        + " \"item\": \"1 package baking powder\",\n"
                        + " \"aisle\": \"Baking\",\n"
                        + " \"parse\": true\n"
                        + "}")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/mealplanner/" + getUserName() + "/shopping-list/items")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("id")
                .toString();

    tearDown(getURL() + "/mealplanner/" + getUserName() + "/shopping-list/items/" + id);
    }
}

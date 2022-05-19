package hw3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public class CuisineTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (American)")
    void postClassifyCuisineAmericanTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/recipes/cuisine")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasEntry("cuisine", "American"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("american"));
        assertThat(response.get("cuisines"), hasItem("American"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (Italian)")
    void postClassifyCuisineMediterraneanTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Pizza")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/recipes/cuisine")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasEntry("cuisine", "Italian"));
        assertThat(response.get("cuisine"), equalToIgnoringCase("italian"));
        assertThat(response.get("cuisines"), hasItem("Italian"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine - Confidence not null")
    void postClassifyCuisineConfidenceNotNullTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/recipes/cuisine")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasKey("confidence"));
        assertThat(response.get("confidence"), not(equalTo(0f)));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine - Cuisines type")
    void postClassifyCuisineCuisinesTypeTest() throws IOException {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/recipes/cuisine")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();

        assertThat(response.get(), hasKey("cuisines"));
        assertThat(response.get("cuisines") instanceof ArrayList, is(true));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine -  Schema is valid")
    void postClassifyCuisineSchemaIsValidTest() throws IOException {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
//                .log()
//                .all()
                .when()
                .post(getURL() + "/recipes/cuisine")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemaCuisine.json"));
    }
}

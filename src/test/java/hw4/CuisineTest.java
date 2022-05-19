package hw4;

import hw4.dto.response.CuisineResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Backend Java. Homework 4
 *
 * @author Vitalii Luzhnov
 * @version 17.05.2022
 */
public class CuisineTest extends AbstractTest {

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (American)")
    void postClassifyCuisineAmericanTest() throws IOException {
        CuisineResponse response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(CuisineResponse.class);

        assertThat(response.getCuisine(), containsString("American"));
        assertThat(response.getCuisine(), equalToIgnoringCase("american"));
        assertThat(response.getCuisines(), hasItem("American"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine (Mediterranean)")
    void postClassifyCuisineMediterraneanTest() throws IOException {
        CuisineResponse response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "Pizza")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(CuisineResponse.class);

        assertThat(response.getCuisine(), containsString("Italian"));
        assertThat(response.getCuisine(), equalToIgnoringCase("Italian"));
        assertThat(response.getCuisines(), hasItem("Italian"));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine - Confidence not null")
    void postClassifyCuisineConfidenceNotNullTest() throws IOException {
        CuisineResponse response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(CuisineResponse.class);

        assertThat(response.getConfidence(), not(equalTo(0f)));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine - Cuisines type")
    void postClassifyCuisineCuisinesTypeTest() throws IOException {
        CuisineResponse response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .extract()
                .body()
                .as(CuisineResponse.class);

        assertThat(response.getCuisines() instanceof ArrayList, is(true));
    }

    @Test
    @Tag("Positive")
    @DisplayName("POST. Classify Cuisine -  Schema is valid")
    void postClassifyCuisineSchemaIsValidTest() throws IOException {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("title", "The Blarney Burger")
                .when()
                .post(getURL() + "/recipes/cuisine")
                .then()
                .spec(responseSpecification)
                .body(matchesJsonSchemaInClasspath("schemaCuisine.json"));
    }
}

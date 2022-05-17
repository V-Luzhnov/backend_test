package hw4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

/**
 * Backend Java. Homework 4
 *
 * @author Vitalii Luzhnov
 * @version 17.05.2022
 */
public abstract class AbstractTest {

    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;

    final static java.util.Properties prop = new java.util.Properties();

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void beforeTest() throws IOException {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();

//        RestAssured.responseSpecification = responseSpecification;

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .addQueryParam("includeNutrition", "false")
                .setContentType(ContentType.JSON)
//                .log(LogDetail.ALL)
                .build();

//        RestAssured.requestSpecification = requestSpecification;
    }

    public static String getURL() throws IOException {
        loadProperties();
        return prop.getProperty("baseURL");
    }

    public static String getApiKey() throws IOException {
        loadProperties();
        return prop.getProperty("apiKey");
    }

    public static String getHash() throws IOException {
        loadProperties();
        return prop.getProperty("hash");
    }

    public static String getUserName() throws IOException {
        loadProperties();
        return prop.getProperty("userName");
    }

    private static void loadProperties() throws IOException {
        try(FileInputStream configFile = new FileInputStream("src/test/resources/propertiesForTest.properties")){
            prop.load(configFile);
        }
    }

    static void tearDown(String url) throws IOException {
        given()
                .queryParam("hash", getHash())
                .queryParam("apiKey", getApiKey())
                .delete(url)
                .then()
                .statusCode(200);
    }
}

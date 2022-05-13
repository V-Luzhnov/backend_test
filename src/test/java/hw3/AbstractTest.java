package hw3;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public abstract class AbstractTest {

    final static java.util.Properties prop = new java.util.Properties();

    @BeforeAll
    static void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    static Integer getSize(JsonPath response) {
        return new ArrayList<>(response.get("results")).size() - 1;
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

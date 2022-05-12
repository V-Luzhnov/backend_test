package hw3;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Backend Java. Homework 3
 *
 * @author Vitalii Luzhnov
 * @version 12.05.2022
 */
public abstract class AbstractTest {

    final static java.util.Properties prop = new java.util.Properties();

    private static void loadProperties() throws IOException {
        try(FileInputStream configFile = new FileInputStream("src/test/resources/propertiesForTest.properties")){
            prop.load(configFile);
        }
    }

    public static String getURL() throws IOException {
        loadProperties();
        return prop.getProperty("baseURL");
    }

    public static String getApiKey() throws IOException {
        loadProperties();
        return prop.getProperty("apiKey");
    }
}

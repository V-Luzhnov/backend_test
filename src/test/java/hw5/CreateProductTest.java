package hw5;

import com.github.javafaker.Faker;
import hw5.api.ProductService;
import hw5.dto.Product;
import hw5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Backend Java. Homework 5
 *
 * @author Vitalii Luzhnov
 * @version 19.05.2022
 */
public class CreateProductTest {

    static ProductService productService;
    Product product = null;
    Faker faker = new Faker();
    String category;
    int id;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

//    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withPrice((int) (Math.random() * 10000))
                .withCategoryTitle(category);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Product creation (Positive)")
    void createProductTest() throws IOException {
        category = "Food";
        setUp();
        Response<Product> response = productService.createProduct(product).execute();

        assertThat(response.code(), equalTo(201));
        assert response.body() != null;
        assertThat(response.body().getCategoryTitle(), equalTo(category));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id =  response.body().getId();
        tearDown();

        category = "Electronic";
        setUp();
        response = productService.createProduct(product).execute();

        assertThat(response.code(), equalTo(201));
        assert response.body() != null;
        assertThat(response.body().getCategoryTitle(), equalTo(category));
        assertThat(response.isSuccessful(), CoreMatchers.is(true));

        id =  response.body().getId();
        tearDown();
    }

    @SneakyThrows
//    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}

package hw5;

import hw5.api.ProductService;
import hw5.dto.Product;
import hw5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Backend Java. Homework 5
 *
 * @author Vitalii Luzhnov
 * @version 19.05.2022
 */
public class ChangeProductTest {

    static ProductService productService;
    Product product = null;
    int id = 1;
    String title;
    int price;
    String category;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    void setUp() {
        product = new Product()
                .withId(id)
                .withTitle(title)
                .withPrice(price)
                .withCategoryTitle(category);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Change product (Positive)")
    void changeProductPositiveTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        String titleOld = response.body().getTitle();
        int priceOld = response.body().getPrice();
        String categoryOld = response.body().getCategoryTitle();

        title = "TV set for Test";
        price = 12345;
        category = "Electronic";

        assertThat(title != titleOld, CoreMatchers.is(true));
        assertThat(price != priceOld, CoreMatchers.is(true));
        assertThat(category != categoryOld, CoreMatchers.is(true));

        setUp();
        response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.code(), equalTo(200));
        assert response.body() != null;
        assertThat(response.body().getTitle() != titleOld, is (true));
        assertThat(response.body().getPrice() != priceOld, is (true));
        assertThat(response.body().getCategoryTitle() != categoryOld, is (true));

        title = titleOld;
        price = priceOld;
        category = categoryOld;

        tearDown();
    }

    @Test
    @Tag("Negative")
    @DisplayName("Change product (Negative)")
    void changeProductNegativeTest() throws IOException {
        Response<Product> response = productService.getProductById(id).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assert response.body() != null;
        title = response.body().getTitle();
        price = response.body().getPrice();
        category = "Auto";

        setUp();
        response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(false));
        assertThat(response.code(), equalTo(500));
    }

    @SneakyThrows
    void tearDown() {
        setUp();
        Response<Product> response = productService.modifyProduct(product).execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}

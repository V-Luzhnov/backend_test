package hw5.api;

import hw5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Backend Java. Homework 5
 *
 * @author Vitalii Luzhnov
 * @version 19.05.2022
 */
public interface ProductService {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product modifyProductRequest);

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") int id);

    @GET("products")
    Call<ResponseBody> getProducts();

}

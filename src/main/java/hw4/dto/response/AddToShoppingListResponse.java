package hw4.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Backend Java. Homework 4
 *
 * @author Vitalii Luzhnov
 * @version 17.05.2022
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "aisles",
        "cost",
        "startDate",
        "endDate"
})
@Data
public class AddToShoppingListResponse {

    @JsonProperty("aisles")
    private List<Aisle> aisles = new ArrayList<Aisle>();
    @JsonProperty("cost")
    private Double cost;
    @JsonProperty("startDate")
    private Integer startDate;
    @JsonProperty("endDate")
    private Integer endDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "aisle",
            "items"
    })
    @Data
    private static class Aisle {
        @JsonProperty("aisle")
        private String aisle;
        @JsonProperty("items")
        private List<Item> items = new ArrayList<Item>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name",
            "measures",
            "pantryItem",
            "aisle",
            "cost",
            "ingredientId"
    })
    @Data
    private static class Item {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("measures")
        private Measures measures;
        @JsonProperty("pantryItem")
        private Boolean pantryItem;
        @JsonProperty("aisle")
        private String aisle;
        @JsonProperty("cost")
        private Double cost;
        @JsonProperty("ingredientId")
        private Integer ingredientId;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "original",
            "metric",
            "us"
    })
    @Data
    private static class Measures {
        @JsonProperty("original")
        private Original original;
        @JsonProperty("metric")
        private Metric metric;
        @JsonProperty("us")
        private Us us;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "amount",
            "unit"
    })
    @Data
    private static class Original {
        @JsonProperty("amount")
        private Double amount;
        @JsonProperty("unit")
        private String unit;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "amount",
            "unit"
    })
    @Data
    private static class Metric {
        @JsonProperty("amount")
        private Double amount;
        @JsonProperty("unit")
        private String unit;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "amount",
            "unit"
    })
    @Data
    private static class Us {
        @JsonProperty("amount")
        private Double amount;
        @JsonProperty("unit")
        private String unit;
    }
}

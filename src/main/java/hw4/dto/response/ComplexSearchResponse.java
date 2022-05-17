package hw4.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backend Java. Homework 4
 *
 * @author Vitalii Luzhnov
 * @version 17.05.2022
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "results",
        "offset",
        "number",
        "totalResults"
})
@Data
public class ComplexSearchResponse {

    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("totalResults")
    private Integer totalResults;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "vegetarian",
            "vegan",
            "glutenFree",
            "dairyFree",
            "veryHealthy",
            "cheap",
            "veryPopular",
            "sustainable",
            "weightWatcherSmartPoints",
            "gaps",
            "lowFodmap",
            "aggregateLikes",
            "spoonacularScore",
            "healthScore",
            "creditsText",
            "license",
            "sourceName",
            "pricePerServing",
            "id",
            "title",
            "readyInMinutes",
            "servings",
            "sourceUrl",
            "image",
            "imageType",
            "nutrition",
            "summary",
            "cuisines",
            "dishTypes",
            "diets",
            "occasions",
            "analyzedInstructions",
            "spoonacularSourceUrl"
    })
    @Data
    public static class Result {
        @JsonProperty("vegetarian")
        private Boolean vegetarian;
        @JsonProperty("vegan")
        private Boolean vegan;
        @JsonProperty("glutenFree")
        private Boolean glutenFree;
        @JsonProperty("dairyFree")
        private Boolean dairyFree;
        @JsonProperty("veryHealthy")
        private Boolean veryHealthy;
        @JsonProperty("cheap")
        private Boolean cheap;
        @JsonProperty("veryPopular")
        private Boolean veryPopular;
        @JsonProperty("sustainable")
        private Boolean sustainable;
        @JsonProperty("weightWatcherSmartPoints")
        private Integer weightWatcherSmartPoints;
        @JsonProperty("gaps")
        private String gaps;
        @JsonProperty("lowFodmap")
        private Boolean lowFodmap;
        @JsonProperty("preparationMinutes")
        private Integer preparationMinutes;
        @JsonProperty("cookingMinutes")
        private Integer cookingMinutes;
        @JsonProperty("aggregateLikes")
        private Integer aggregateLikes;
        @JsonProperty("spoonacularScore")
        private Double spoonacularScore;
        @JsonProperty("healthScore")
        private Double healthScore;
        @JsonProperty("creditsText")
        private String creditsText;
        @JsonProperty("license")
        private String license;
        @JsonProperty("sourceName")
        private String sourceName;
        @JsonProperty("pricePerServing")
        private Double pricePerServing;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("readyInMinutes")
        private Integer readyInMinutes;
        @JsonProperty("servings")
        private Integer servings;
        @JsonProperty("sourceUrl")
        private String sourceUrl;
        @JsonProperty("image")
        private String image;
        @JsonProperty("imageType")
        private String imageType;
        @JsonProperty("nutrition")
        public Nutrition nutrition;
        @JsonProperty("summary")
        private String summary;
        @JsonProperty("cuisines")
        private List<String> cuisines = new ArrayList<String>();
        @JsonProperty("dishTypes")
        private List<String> dishTypes = new ArrayList<String>();
        @JsonProperty("diets")
        private List<String> diets = new ArrayList<String>();
        @JsonProperty("occasions")
        private List<Object> occasions = new ArrayList<Object>();
        @JsonProperty("analyzedInstructions")
        private List<AnalyzedInstruction> analyzedInstructions = new ArrayList<AnalyzedInstruction>();
        @JsonProperty("spoonacularSourceUrl")
        private String spoonacularSourceUrl;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "nutrients"
    })
    @Data
    public static class Nutrition {
        @JsonProperty("nutrients")
        private List<Nutrient> nutrients = new ArrayList<Nutrient>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "name",
            "amount",
            "unit"
    })
    @Data
    public static class Nutrient {
        @JsonProperty("name")
        private String name;
        @JsonProperty("amount")
        private Float amount;
        @JsonProperty("unit")
        private String unit;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "name",
            "steps"
    })
    @Data
    public static class AnalyzedInstruction {
        @JsonProperty("name")
        private String name;
        @JsonProperty("steps")
        private List<Step> steps = new ArrayList<Step>();
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "number",
            "step",
            "ingredients",
            "equipment"
    })
    @Data
    public static class Step {
        @JsonProperty("number")
        public Integer number;
        @JsonProperty("step")
        public String step;
        @JsonProperty("ingredients")
        public List<Ingredient> ingredients = new ArrayList<Ingredient>();
        @JsonProperty("equipment")
        public List<Equipment> equipment = new ArrayList<Equipment>();
        @JsonProperty("length")
        public Length length;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name",
            "localizedName",
            "image"
    })
    @Data
    public static class Ingredient {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("localizedName")
        private String localizedName;
        @JsonProperty("image")
        private String image;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "name",
            "localizedName",
            "image"
    })
    @Data
    public static class Equipment {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("localizedName")
        private String localizedName;
        @JsonProperty("image")
        private String image;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "number",
            "unit"
    })
    @Data
    public static class Length {
        @JsonProperty("number")
        private Integer number;
        @JsonProperty("unit")
        private String unit;
    }
}

package com.hintze.myspringrs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonDeserialize(builder = Fridge.FridgeBuilder.class)
@Data
@Builder
public class Fridge {

    private String id;
    /**
     * {@link ProductItem}s in this Fridge
     */
    private List<ProductItem> inventory;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FridgeBuilder {
    }
}

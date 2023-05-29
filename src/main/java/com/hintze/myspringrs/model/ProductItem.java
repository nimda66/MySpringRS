package com.hintze.myspringrs.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

@JsonDeserialize(builder = ProductItem.ProductItemBuilder.class)
@Data
@Builder
public class ProductItem {

    private int id;

    private String name;

    /**
     * current quantity
     */
    private double actual;

    /**
     * desired quantity
     */
    private double target;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ProductItemBuilder {
    }
}

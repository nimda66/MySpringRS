package com.hintze.myspringrs.service;

import com.hintze.myspringrs.controller.ShoppingRestConsumer;
import com.hintze.myspringrs.model.Fridge;
import com.hintze.myspringrs.model.ProductItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingServiceTest {
    static final String TEST_FRIDGE_ID = "id2";
    static final Fridge TEST_FRIDGE = Fridge.builder().id(TEST_FRIDGE_ID)
            .inventory(Collections.emptyList()).build();

    @Autowired
    ShoppingService shoppingService;

    @MockBean
    ShoppingRestConsumer shoppingRestConsumer;

    @Test
    void getFridge() {

        Fridge testFridge = TEST_FRIDGE;
        given(shoppingRestConsumer.getFridge(TEST_FRIDGE_ID))
                .willReturn(testFridge);

        Assert.notNull(shoppingService.getFridge(TEST_FRIDGE_ID), "testFridge is null"


        );
    }

    @Test
    void createFridge() {

        Fridge testFridge = TEST_FRIDGE;
        given(shoppingRestConsumer.createFridge())
                .willReturn(testFridge);

        Assert.notNull(shoppingService.createFridge(), "testFridge is null");
    }

    @Test
    void addMissingProductItemsToFridge() {

        Set<ProductItem> testProductItems = new HashSet<>();
        ProductItem testProductItem = ProductItem.builder().id(10).build();
        testProductItems.add(testProductItem);

        given(shoppingRestConsumer.addNewProductItem(TEST_FRIDGE_ID, testProductItem))
                .willReturn(testProductItem);
        given(shoppingService.getFridge(TEST_FRIDGE_ID))
                .willReturn(TEST_FRIDGE);

        Assert.notNull(shoppingService.addMissingProductItemsToFridge(TEST_FRIDGE_ID, testProductItems), "testFridge is null");

        then(shoppingRestConsumer)
                .should(times(1))
                .addNewProductItem(TEST_FRIDGE_ID, testProductItem);
    }

    @Test
    void computeMissingProductItems() {

        ProductItem productItem = ProductItem.builder()
                .id(12)
                .name("item12")
                .actual(10.0)
                .target(15.0)
                .build();
        List<ProductItem> productItems = new ArrayList<>();
        productItems.add(productItem);

        Fridge inventoryFridge = Fridge.builder()
                .id(TEST_FRIDGE_ID)
                .inventory(productItems)
                .build();

        given(shoppingService.getFridge(TEST_FRIDGE_ID))
                .willReturn(inventoryFridge);

        Map<ProductItem, Double> resultProductItemQuantityMap = shoppingService.computeMissingProductItems(TEST_FRIDGE_ID);
        Assert.isTrue(resultProductItemQuantityMap.get(productItem) == 5.0, "wrong quantity");
    }

    @Test
    void updateTargetProductItemQuantities() {
        ProductItem desiredProductItem = ProductItem.builder().name("No11").id(11).target(2.0).build();
        List<ProductItem> desiredProductItems = new ArrayList<>();
        desiredProductItems.add(desiredProductItem);
        Fridge desiredFridge = Fridge.builder().id(TEST_FRIDGE_ID).inventory(desiredProductItems).build();

        given(shoppingRestConsumer.getProductItem(TEST_FRIDGE_ID, desiredProductItem.getId()))
                .willReturn(desiredProductItem);
        given(shoppingRestConsumer.updateProductItem(any(), any()))
                .willReturn(desiredProductItem);
        given(shoppingService.getFridge(TEST_FRIDGE_ID))
                .willReturn(TEST_FRIDGE);

        Assert.notNull(shoppingService.updateTargetProductItemQuantities(desiredFridge), "testFridge is null");

        then(shoppingRestConsumer)
                .should(times(1))
                .getFridge(TEST_FRIDGE_ID);

        then(shoppingRestConsumer)
                .should(times(1))
                .updateProductItem(TEST_FRIDGE_ID, desiredProductItem);

    }
}

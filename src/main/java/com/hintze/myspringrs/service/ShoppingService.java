package com.hintze.myspringrs.service;

import com.hintze.myspringrs.controller.ShoppingRestConsumer;
import com.hintze.myspringrs.model.Fridge;
import com.hintze.myspringrs.model.ProductItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ShoppingService {

    @Autowired
    ShoppingRestConsumer shoppingRestConsumer;

    /**
     * get existing fridge
     *
     * @param fridgeId fridge to update
     * @return Fridge
     */
    public Fridge getFridge(String fridgeId) {
        return shoppingRestConsumer.getFridge(fridgeId);
    }

    /**
     * create new fridge
     *
     * @return Fridge
     * @throws RuntimeException if fridgeId is already taken
     */
    public Fridge createFridge() throws RuntimeException {
        return shoppingRestConsumer.createFridge();
    }

    /**
     * @param fridgeId            fridge to update
     * @param desiredProductItems items to add
     * @return updated fridge
     * @throws RuntimeException if fridge not found or empty item list
     */
    public Fridge addMissingProductItemsToFridge(String fridgeId, Set<ProductItem> desiredProductItems) throws RuntimeException {
        Fridge orgFridge = this.getFridge(fridgeId);
        if (orgFridge == null || CollectionUtils.isEmpty(desiredProductItems)) {
            throw new RuntimeException("fridge not found or item list empty");
        }
        desiredProductItems.forEach(productItem -> shoppingRestConsumer.addNewProductItem(fridgeId, productItem));

        return this.getFridge(fridgeId);
    }

    /**
     * @param fridgeId fridge to update
     * @return Map of missing {@link ProductItem}s to missing quantities
     * @throws RuntimeException if fridge not found
     */
    public Map<ProductItem, Double> computeMissingProductItems(String fridgeId) throws RuntimeException {
        Fridge fridge = this.getFridge(fridgeId);
        if (fridge == null) {
            throw new RuntimeException("fridge not found");
        }

        Map<ProductItem, Double> missingProductItemMap = new HashMap<>();
        if (CollectionUtils.isEmpty(fridge.getInventory())) {
            log.warn("no inventory for fridge {}", fridgeId);
            return Collections.emptyMap();
        }

        fridge.getInventory().forEach(productItem -> {
                    if (productItem.getActual() < productItem.getTarget()) {
                        missingProductItemMap.put(productItem, productItem.getTarget() - productItem.getActual());
                    }
                }
        );
        return missingProductItemMap;
    }

    /**
     * update fridge items with new target quantities
     *
     * @param desiredFridge fridge to update with new productItem quantities
     * @return updated fridge
     * @throws RuntimeException if update failed
     */
    public Fridge updateTargetProductItemQuantities(Fridge desiredFridge) throws RuntimeException {
        final String fridgeId;

        try {
            fridgeId = desiredFridge.getId();
            desiredFridge.getInventory().forEach(desiredProductItem -> {

                        double desiredTargetQuantity = desiredProductItem.getTarget();
                        ProductItem orgProductItem = shoppingRestConsumer.getProductItem(fridgeId, desiredProductItem.getId());
                        if (Double.isNaN(desiredTargetQuantity)) {
                            log.warn("fridge {} productItem {} target is NaN", fridgeId, desiredProductItem.getId());
                            return;
                        }
                        orgProductItem.setTarget(desiredTargetQuantity);
                        ProductItem resultProductItem = shoppingRestConsumer.updateProductItem(fridgeId, orgProductItem);
                        log.debug("update quantity for fridge: {} item: {}", fridgeId, resultProductItem.getName());
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("quantity update failed", e);
        }

        return this.getFridge(fridgeId);
    }

}

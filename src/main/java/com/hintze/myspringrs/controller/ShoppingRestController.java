package com.hintze.myspringrs.controller;

import com.hintze.myspringrs.model.Fridge;
import com.hintze.myspringrs.model.ProductItem;
import com.hintze.myspringrs.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Diese soll erm&ouml;glichen bei einem Einkauf einfach abfragen zu k&ouml;nnen,
 * welche Produkte in welcher Menge eingekauft werden m&uuml;ssen.
 */
@Slf4j
@RestController
@RequestMapping("/coolschrank")
public class ShoppingRestController {

    @Autowired
    protected ShoppingService shoppingService;

    /**
     * 1. Spezielle, h&auml;ufig genutzte Produkte,
     * die im K&uuml;hlschrank fehlen,
     * auf eine Einkaufsliste schreiben.
     *
     * @param fridgeId            fridge to update
     * @param desiredProductItems to add to fridge
     * @return updated fridge
     */
    @PostMapping(value = "/missingproducts/{fridgeId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addMissingProductItemsToFridge(@PathVariable String fridgeId, @RequestBody Set<ProductItem> desiredProductItems) {
        try {
            Fridge resultFridge = shoppingService.addMissingProductItemsToFridge(fridgeId, desiredProductItems);
            return new ResponseEntity(resultFridge, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            log.warn("addMissingProductItemsToFridge failed for fridge {}  {}", fridgeId, e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 2. Es wird eine neue Schnittstelle ben&ouml;tigt,
     * die in der Frontend-App verwendet werden soll.
     * Diese soll erm&ouml;glichen bei einem Einkauf einfach
     * abfragen zu k&ouml;nnen, welche Produkte in welcher
     * Menge eingekauft werden m&uuml;ssen.
     *
     * @param fridgeId to count quantities from
     * @return Map of {@link ProductItem}s to missing quantities
     */
    @GetMapping(value = "/missingproducts/{fridgeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<ProductItem, Double>> getMissingProductItems(@PathVariable String fridgeId) {
        try {
            Map<ProductItem, Double> missingProductItems = shoppingService.computeMissingProductItems(fridgeId);
            return new ResponseEntity<>(missingProductItems, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            log.warn("getMissingProductItems failed for fridge {}  {}", fridgeId, e.getMessage());
            return new ResponseEntity<>(Collections.emptyMap(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 3. Die gew&uuml;nschten Produkt-Mengen
     * k&ouml;nnen zur Laufzeit hinzugef&uuml;gt werden.
     *
     * @param newFridge with new desired productItem quantities in {@link ProductItem}.target, leave {@link ProductItem}.actual untouched
     * @return updated fridge
     */
    @PostMapping(value = "/desiredquantity",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDesiredProductItemQuantities(@RequestBody Fridge newFridge) {
        String newFridgeId = "";
        try {
            newFridgeId = newFridge.getId();
            Fridge resultFridge = shoppingService.updateTargetProductItemQuantities(newFridge);
            return new ResponseEntity(resultFridge, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.warn("getMissingProductItems failed for fridge {}  {}", newFridgeId, e.getMessage());
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * ADDITIONAL #B
     * gets fridge that exists
     *
     * @param fridgeId of fridge
     * @return fridge or not found
     */
    @GetMapping(value = "/fridge/{fridgeId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFridge(@PathVariable String fridgeId) {
        Fridge fridge = shoppingService.getFridge(fridgeId);
        if (fridge != null) {
            return new ResponseEntity(fridge, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * ADDITIONAL #C
     * create fridge, if that fridgeId is not taken
     *
     * @return fridge or conflict, if fridgeId is already taken
     */
    @PostMapping(value = "/fridge",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createFridge() {
        try {
            Fridge fridge = shoppingService.createFridge();
            return new ResponseEntity(fridge, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.info("Fail" + e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

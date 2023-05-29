package com.hintze.myspringrs.controller;

import com.hintze.myspringrs.model.Fridge;
import com.hintze.myspringrs.model.ProductItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class ShoppingRestConsumer {


    static final String BASE_REST_URI = "https://innovations.rola.com/build/rola/coolschrank/ongoing/application";

    @Autowired
    RestTemplate restTemplate;

    /*FRIDGE OPERATIONS*/

    /**
     * POST /fridge
     * Create Fridge
     * Legt einen neuen K&uuml;hlschrank an und gibt die Adresse zur&uuml;ck
     * unter der dieser K&uuml;hlschrank zu erreichen ist.
     *
     * @return created fridge
     */
    public Fridge createFridge() {

        ResponseEntity<Fridge> responseEntity = restTemplate.exchange(
                BASE_REST_URI + "/fridge",
                HttpMethod.POST,
                new HttpEntity<>(new HttpHeaders()),
                Fridge.class);

        return responseEntity.getBody();
    }

    /**
     * GET /fridge/{id}
     * Get fridge
     * Liefert den Gesamtzustand des referenzierten K&uuml;hlschranks.
     *
     * @param fridgeId
     * @return Fridge
     */
    public Fridge getFridge(String fridgeId) {
        ResponseEntity<Fridge> responseEntity = restTemplate.exchange(
                BASE_REST_URI + "/fridge/" + fridgeId,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Fridge.class);

        return responseEntity.getBody();
    }

    /**
     * POST /fridge/{id}/item
     * Add new item
     * Legt einen neuen K&uuml;hlschrank-Inhalt an und gibt
     * die Adresse zur&uuml;ck unter der dieser neue Inhalt zu erreichen ist.
     *
     * @param fridgeId
     * @param productItem add to this fridge
     * @return created Item
     */
    public ProductItem addNewProductItem(String fridgeId, ProductItem productItem) {
        ResponseEntity<ProductItem> responseEntity = restTemplate.exchange(
                BASE_REST_URI + "/fridge/" + fridgeId + "/item",
                HttpMethod.POST,
                new HttpEntity<>(productItem),
                ProductItem.class);

        return responseEntity.getBody();
    }

    /*ITEM (PRODUCT) OPERATIONS*/

    /**
     * GET /fridge/{id}/item/{itemId}
     * Get specific item
     * Liefert den Zustand des refernzierten K&uuml;hlschrank-Inhalt.
     *
     * @param fridgeId
     * @param productItemId
     * @return ProductItem
     */
    public ProductItem getProductItem(String fridgeId, Integer productItemId) {
        ResponseEntity<ProductItem> responseEntity = restTemplate.exchange(
                BASE_REST_URI + "/fridge/" + fridgeId + "/item/" + productItemId,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                ProductItem.class);

        return responseEntity.getBody();
    }

    /**
     * POST /fridge/{id}/item/{itemId}
     * Change specific item
     * &Auml;ndert/Aktualisiert den referenzierten K&uuml;hlschrank-Inhalt und gibt,
     * nach erfolgter &auml;nderung, den neuen Zustand dieses Inhalts zur&uuml;ck.
     *
     * @param fridgeId
     * @param productItem
     * @return updated ProductItem
     */
    public ProductItem updateProductItem(String fridgeId, ProductItem productItem) {
        ResponseEntity<ProductItem> responseEntity = restTemplate.exchange(
                BASE_REST_URI + "/fridge/" + fridgeId + "/item" + productItem.getId(),
                HttpMethod.POST,
                new HttpEntity<>(new HttpHeaders()),
                ProductItem.class);

        return responseEntity.getBody();
    }

}

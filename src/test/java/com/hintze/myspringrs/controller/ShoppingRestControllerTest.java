package com.hintze.myspringrs.controller;

import com.hintze.myspringrs.model.Fridge;
import com.hintze.myspringrs.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ShoppingRestControllerTest {

    static final String BASE = "http://localhost:8080/coolschrank";
    static final String TEST_CONTENT = "{}";
    static final String TEST_FRIDGE_ID = "id1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShoppingService shoppingService;

    @Test
    void addMissingProductItemsToFridge() throws Exception {

        mockMvc.perform(post(BASE + "/missingproducts/{fridgeId}", TEST_FRIDGE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void getMissingProductItems() throws Exception {

        mockMvc.perform(get(BASE + "/missingproducts/{fridgeId}", TEST_FRIDGE_ID))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void updateDesiredProductItemQuantities() throws Exception {

        mockMvc.perform(post(BASE + "/desiredquantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TEST_CONTENT))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    void getFridge() throws Exception {

        Fridge testFridge = Fridge.builder()
                .id(TEST_FRIDGE_ID)
                .inventory(Collections.emptyList())
                .build();
        given(shoppingService.getFridge(TEST_FRIDGE_ID))
                .willReturn(testFridge);

        mockMvc.perform(get(BASE + "/fridge/{fridgeId}", TEST_FRIDGE_ID))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn();
    }

    @Test
    void createFridge() throws Exception {
        mockMvc.perform(post(BASE + "/fridge", TEST_FRIDGE_ID))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andReturn();
    }
}
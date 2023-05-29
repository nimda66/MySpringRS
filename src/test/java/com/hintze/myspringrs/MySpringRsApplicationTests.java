package com.hintze.myspringrs;

import com.hintze.myspringrs.controller.ShoppingRestConsumer;
import com.hintze.myspringrs.controller.ShoppingRestController;
import com.hintze.myspringrs.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class MySpringRsApplicationTests {


    @Autowired
    private ShoppingRestController shoppingController;

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ShoppingRestConsumer shoppingRestConsumer;

    @Test
    void contextLoads() {
        Assert.notNull(shoppingService, "shoppingService is null");
        Assert.notNull(shoppingController, "shoppingController is null");
        Assert.notNull(shoppingRestConsumer, "shoppingRestConsumer is null");
    }

}

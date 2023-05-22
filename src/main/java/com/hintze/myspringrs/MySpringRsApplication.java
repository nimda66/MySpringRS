package com.hintze.myspringrs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.out;

@Slf4j
@SpringBootApplication
public class MySpringRsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringRsApplication.class, args);
        out.println("Hallo Welt");
    }

}

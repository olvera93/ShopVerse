package com.olvera.shopverseproducto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class ShopVerseProductoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopVerseProductoApplication.class, args);
    }

}

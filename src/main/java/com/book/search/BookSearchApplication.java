package com.book.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSearchApplication.class, args);
    }

}

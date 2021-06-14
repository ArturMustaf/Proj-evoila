package com.basecrawler;


import com.basecrawler.service.QuoteClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BaseCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseCrawlerApplication.class, args);
        var client = new QuoteClient();

        System.out.println(client.getQuote());
    }
}

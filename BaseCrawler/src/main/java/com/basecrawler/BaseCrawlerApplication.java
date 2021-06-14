package com.basecrawler;


import com.basecrawler.service.QuoteClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class BaseCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseCrawlerApplication.class, args);

    }

    @Scheduled(fixedRate = 1000)
    public void printQuote(){
        var client = new QuoteClient();
        System.out.println(client.getQuote());
    }

}

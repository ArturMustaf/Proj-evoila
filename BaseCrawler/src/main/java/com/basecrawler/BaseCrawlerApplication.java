package com.basecrawler;


import com.basecrawler.service.QuoteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class BaseCrawlerApplication {
    final Logger logger = LoggerFactory.getLogger(BaseCrawlerApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BaseCrawlerApplication.class, args);
    }

    @Scheduled(fixedRate = 1000)
    public void printQuote(){
        var client = new QuoteClient();
        var reply = client.getQuote().toString();
        logger.info(reply);
    }

}

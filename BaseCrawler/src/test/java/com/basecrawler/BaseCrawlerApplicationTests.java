package com.basecrawler;

import com.basecrawler.service.QuoteClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BaseCrawlerApplicationTests {
    final QuoteClient client = new QuoteClient();
    @Test
    void contextLoads() {
        assertNotNull(client);
    }

}

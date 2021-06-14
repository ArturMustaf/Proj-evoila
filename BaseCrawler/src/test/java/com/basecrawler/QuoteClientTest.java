package com.basecrawler;

import com.basecrawler.service.QuoteClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuoteClientTest {

    @Test
    void getQuote() {
        var client = new QuoteClient();
        var quote = client.getQuote();
        assertTrue(quote!=null);
    }
}

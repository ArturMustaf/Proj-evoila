package com.basecrawler;

import com.basecrawler.service.QuoteClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class QuoteClientTest {

    @Test
    void getQuoteTest() {
        var client = new QuoteClient();
        var quote = client.getQuote();
        assertNotNull(quote);
    }

}

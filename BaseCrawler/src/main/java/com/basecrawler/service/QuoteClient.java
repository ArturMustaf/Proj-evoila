package com.basecrawler.service;

import com.basecrawler.models.Quote;
import org.springframework.web.reactive.function.client.WebClient;

public class QuoteClient {
    // /random
    final WebClient client = WebClient.create("https://quoters.apps.pcfone.io/api");

    public Quote getQuote() {
        return client.get().uri("/random")
                .retrieve()
                .bodyToMono(Quote.class)
                .block();
    }
}

package com.basecrawler.service;

import com.basecrawler.models.Quote;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class QuoteClient {
    // /random
    WebClient client = WebClient.create("https://quoters.apps.pcfone.io/api");

    public Quote getQuote() {
        return client.get().uri("/random")
                .retrieve()
                .bodyToFlux(Quote.class)
                .blockFirst();
    }
}

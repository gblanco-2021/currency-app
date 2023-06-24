package com.currency.app.currencyapp.webflux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

//@Component
public class HelloHandler {

//    public Mono<ServerResponse> showMonoMessage(ServerRequest request) {
//        return ServerResponse.ok()
//                .contentType(MediaType.TEXT_PLAIN)
//                .body(Mono.just("welcom to my channel"), String.class);
//    }

//    public Mono<ServerResponse> showFluxMessage(ServerRequest request) {
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_STREAM_JSON)
//                .body(Flux.just("welcom", "to", "my", "channel")
//                        .delayElements(Duration.ofSeconds(1)), String.class);
//    }

}

package ru.veretennikov.notificationsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;
import ru.veretennikov.notificationsystem.service.PingService;

import static org.springframework.http.MediaType.TEXT_PLAIN;

@Slf4j
@Component
public class UrlUnavailableHandler {

    private final PingService pingService;

    public UrlUnavailableHandler(PingService pingService) {
        this.pingService = pingService;
    }

    public Mono<ServerResponse> handle(ServerRequest request){

        return request.bodyToMono(UnvlbReq.class)
                .map(unvlbReq -> {
                    log.debug("from {} to {}", unvlbReq.getMsisdnA(), unvlbReq.getMsisdnB());
                    pingService.ping(unvlbReq);
                    return unvlbReq;
                })
                .flatMap(this::toServerResponse);

    }

    private Mono<ServerResponse> toServerResponse(UnvlbReq unvlbReq) {
        return ServerResponse.ok()
                .contentType(TEXT_PLAIN)
                .body(BodyInserters.fromValue("Сообщение получено и будет обработано"))
//                .build()
                ;
    }

}

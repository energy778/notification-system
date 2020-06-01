package ru.veretennikov.notificationsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.veretennikov.notificationsystem.controller.UrlUnavailableHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UrlUnavailableSubscriber {

    /**
     * роутер прослушивает unavailable URL и возвращает значение, предоставляемое нашим реактивным классом обработчика
     * */
    @Bean
    public RouterFunction<ServerResponse> urlUnavailableRoute(UrlUnavailableHandler urlUnavailableHandler, AppProperty appProperty){
        return route(POST(appProperty.getUnavailableUrlPrefix()), urlUnavailableHandler::handle);
    }

}

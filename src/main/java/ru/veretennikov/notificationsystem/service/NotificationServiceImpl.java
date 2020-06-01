package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.veretennikov.notificationsystem.config.AppProperty;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;
import ru.veretennikov.notificationsystem.dto.Notification;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final WebClient client;

    public NotificationServiceImpl(AppProperty appProperty) {

        this.client = WebClient.builder()
                .baseUrl(appProperty.getNotifyUrl())
                .build();

    }

    @Override
    public void startNotify(UnvlbReq request) {

        log.debug("Уведомляем {} о том, что {} появился в сети", request.getMsisdnA(), request.getMsisdnB());

        String template = String.format("%s: Этот абонент снова в сети", request.getMsisdnB());

        Notification notification = new Notification();
        notification.setMsisdnA(request.getMsisdnB());
        notification.setMsisdnB(request.getMsisdnA());
        notification.setText(template);

        client.post()
                .body(BodyInserters.fromValue(notification))
                .exchange()
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe()      // ?
        ;

    }

}

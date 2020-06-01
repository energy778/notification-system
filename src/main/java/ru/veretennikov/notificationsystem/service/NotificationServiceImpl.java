package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.veretennikov.notificationsystem.config.AppProperty;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;
import ru.veretennikov.notificationsystem.dto.Notification;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final Locale locale;
    private final WebClient client;
    private final MessageSource messageSource;

    public NotificationServiceImpl(AppProperty appProperty, MessageSource messageSource) {
        this.locale = new Locale(appProperty.getNotifyLanguage(), appProperty.getNotifyCountry());
        this.messageSource = messageSource;
        this.client = WebClient.builder()
                .baseUrl(appProperty.getNotifyUrl())
                .build();
    }

    @Override
    public void startNotify(UnvlbReq request, Instant timeInstant) {

        log.debug("уведомляем {} о том, что {} появился в сети", request.getMsisdnA(), request.getMsisdnB());

        // TODO: 01.06.2020 пока для примера
        ZoneId timeZone = ZoneId.of("Australia/Brisbane");
//        System.out.println(timeInstant.atZone(ZoneId.systemDefault()).toLocalDateTime());
//        System.out.println(timeInstant.atZone(ZoneId.of("Asia/Yekaterinburg")).toLocalDateTime());
//        System.out.println(timeInstant.atZone(ZoneId.of("Australia/Brisbane")).toLocalDateTime());

        ZonedDateTime zonedDateTime = timeInstant.atZone(timeZone);

        String template = messageSource
                .getMessage("message.notification",
                        new Object[] {request.getMsisdnB(), zonedDateTime.format(new DateTimeFormatterBuilder().appendPattern("hh:mm:ss  dd MMMM yyyy").toFormatter())},
                        locale);

        Notification notification = new Notification();
        notification.setMsisdnA(request.getMsisdnB());
        notification.setMsisdnB(request.getMsisdnA());
        notification.setText(template);

        int curHour = zonedDateTime.getHour();
        if (curHour >= 9 && curHour <= 22)
            client.post()
                    .body(BodyInserters.fromValue(notification))
                    .exchange()
                    .doOnError(throwable -> log.error(throwable.getMessage()))
                    .subscribe()      // ?
            ;
        else
//            FIXME
            log.debug(String.format("Сейчас %s. Сообщение будет отправлено в следующее временное окно",
                    zonedDateTime.format(new DateTimeFormatterBuilder().appendPattern("dd MMMM yyyy, hh:mm:ss, zzzz").toFormatter())));

    }

}

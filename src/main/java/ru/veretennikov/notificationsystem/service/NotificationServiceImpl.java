package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.veretennikov.notificationsystem.config.AppProperty;
import ru.veretennikov.notificationsystem.dto.Notification;
import ru.veretennikov.notificationsystem.dto.UnvlbReq;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final ProfileService profileService;
    private final MessageSource messageSource;
    private final ZoneId defTimeZone;
    private final Locale defLocale;
    private final WebClient client;

    public NotificationServiceImpl(AppProperty appProperty, MessageSource messageSource, ProfileService profileService) {
        this.profileService = profileService;
        this.messageSource = messageSource;
//        this.defTimeZone = ZoneId.of("Australia/Brisbane");
        this.defTimeZone = ZoneId.systemDefault();
        this.defLocale = new Locale(appProperty.getDefaultNotifyLanguage(), appProperty.getDefaultNotifyCountry());
        this.client = WebClient.builder()
                .baseUrl(appProperty.getNotifyUrl())
                .build();
    }

    @Override
    public void startNotify(UnvlbReq request, Instant timeInstant) {

        log.debug("{} is available, notify {}", request.getMsisdnB(), request.getMsisdnA());

        profileService.getProfileByPhoneNumber(request.getMsisdnA())
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .map(profile -> {
                    log.debug(profile.toString());
                    Locale locale;
                    ZoneId timeZone;
                    if (profile.getLang() == null)
                        locale = defLocale;
                    else
                        locale = new Locale(profile.getLang(), profile.getCountry() == null ? "" : profile.getCountry());
                    if (profile.getTimeZone() == null)
                        timeZone = defTimeZone;
                    else
                        timeZone = ZoneId.of(profile.getTimeZone());
                    return this.sendNotification(request, timeInstant, locale, timeZone);
                })
                .switchIfEmpty(result -> {
                    log.debug("No profile in DB with phone number of {}. Use default profile", request.getMsisdnA());
                    Mono.just(this.sendNotification(request, timeInstant, defLocale, defTimeZone));
                })
                .subscribe();

    }

    private Notification sendNotification(UnvlbReq request, Instant timeInstant, Locale locale, ZoneId timeZone) {

        ZonedDateTime zonedDateTime = timeInstant.atZone(timeZone);

        String textMessage = messageSource
                .getMessage("message.notification",
                        new Object[] {request.getMsisdnB(), zonedDateTime.format(new DateTimeFormatterBuilder().appendPattern("hh:mm:ss dd MMMM yyyy").toFormatter())},
                        locale);

        Notification notification = Notification.builder()
                .msisdnA(request.getMsisdnB())
                .msisdnB(request.getMsisdnA())
                .text(textMessage)
                .build();

        int curHour = zonedDateTime.getHour();
        if (curHour >= 9 && curHour <= 22)
            client.post()
                    .body(BodyInserters.fromValue(notification))
                    .exchange()
                    .doOnError(throwable -> log.error(throwable.getMessage()))
                    .subscribe();
        else
//            FIXME
            log.debug(String.format("now %s. Message will be sent to the next time window",
                    zonedDateTime.format(new DateTimeFormatterBuilder().appendPattern("dd MMMM yyyy hh:mm:ss, zzzz").toFormatter())));

        return notification;

    }

}

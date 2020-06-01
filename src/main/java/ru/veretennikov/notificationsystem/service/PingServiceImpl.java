package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.veretennikov.notificationsystem.config.AppProperty;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;
import ru.veretennikov.notificationsystem.dto.AbonentStatus;

import java.time.Duration;

import static ru.veretennikov.notificationsystem.dto.PingStatus.IN_NETWORK;
import static ru.veretennikov.notificationsystem.dto.PingStatus.UNAVAILABLE_SUBSCRIBER;

@Slf4j
@Service
public class PingServiceImpl implements PingService {

    private static final String MSISDN = "msisdn";

    private final AppProperty appProperty;
    private final WebClient client;
    private final ScheduleService scheduleService;
    private final NotificationService notificationService;

    public PingServiceImpl(AppProperty appProperty, ScheduleService scheduleService, NotificationService notificationService) {
        this.appProperty = appProperty;
        this.scheduleService = scheduleService;
        this.notificationService = notificationService;
        this.client = WebClient.builder()
                .baseUrl(appProperty.getPingUrl())
                .build();
    }

    @Override
    public void ping(UnvlbReq request) {

        String abonent = request.getMsisdnB();
        log.debug("пингуем абонента {}", abonent);

        client.post()
                .uri(uriBuilder -> uriBuilder.queryParam(MSISDN, abonent).build())
                .exchange()
                .timeout(Duration.ofDays(3))        // пингуем не дольше 3 суток
                .flatMap(clientResponse -> clientResponse.bodyToMono(AbonentStatus.class))
                .doOnError(throwable -> log.error(throwable.getMessage()))
//                .map(abonentStatus -> {
//                    if (UNAVAILABLE_SUBSCRIBER.equals(abonentStatus.getStatus()))
//                        scheduleService.startSchedule(request);
//                    else if (IN_NETWORK.equals(abonentStatus.getStatus()))
//                        notificationService.startNotify(request);
//                    return abonentStatus;
//                })
                .subscribe(abonentStatus -> this.afterResponse(request, abonentStatus))
        ;

    }

    private void afterResponse(UnvlbReq request, AbonentStatus abonentStatus) {

        if (UNAVAILABLE_SUBSCRIBER.equals(abonentStatus.getStatus())){
//            FIXME
//            scheduleService.startSchedule(request);
            try {
                Thread.sleep(appProperty.getPingSleepInMinutes() *1000 *60);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            this.ping(request);
        }
        else if (IN_NETWORK.equals(abonentStatus.getStatus()))
            notificationService.startNotify(request);

    }

}

package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void startNotify(UnvlbReq request) {
        log.debug("Уведомляем {} о том, что {} появился в сети", request.getMsisdnA(), request.getMsisdnB());
    }
}

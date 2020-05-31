package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veretennikov.notificationsystem.domain.UnvlbReq;

@Slf4j
@Service
public class PingServiceImpl implements PingService {

    @Override
    public void ping(UnvlbReq unvlbReq) {
        log.debug("пингуем абонента {}", unvlbReq.getMsisdnB());
    }

}

package ru.veretennikov.notificationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.veretennikov.notificationsystem.dto.UnvlbReq;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Override
    public void startSchedule(UnvlbReq request) {
        log.debug("По расписанию пингуем доступность абонента {}", request.getMsisdnB());
    }
}

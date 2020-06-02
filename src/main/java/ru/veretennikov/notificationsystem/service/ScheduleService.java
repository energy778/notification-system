package ru.veretennikov.notificationsystem.service;

import ru.veretennikov.notificationsystem.dto.UnvlbReq;

public interface ScheduleService {
    void startSchedule(UnvlbReq request);
}

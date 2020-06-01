package ru.veretennikov.notificationsystem.service;

import ru.veretennikov.notificationsystem.domain.UnvlbReq;

import java.time.Instant;

public interface NotificationService {
    void startNotify(UnvlbReq request, Instant timeInstant);
}

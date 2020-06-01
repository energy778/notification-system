package ru.veretennikov.notificationsystem.dto;

import lombok.Data;

@Data
public class AbonentStatus {
//    private PingStatus status;        // не взлетела сериализация сразу в enum
    private String status;
}

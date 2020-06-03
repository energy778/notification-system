package ru.veretennikov.notificationsystem.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String msisdnA;
    private String msisdnB;
    private String text;
}

package ru.veretennikov.notificationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnvlbReq {

    private String msisdnA;
    private String msisdnB;

}

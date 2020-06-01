package ru.veretennikov.notificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PingStatus {

    UNAVAILABLE_SUBSCRIBER("unavailableSubscriber"),
    IN_NETWORK("inNetwork");

    private String name;

    PingStatus(String name) {
        this.name = name;
    }

    @JsonCreator
    public static PingStatus fromString(String name) {
        return name == null
                ? null
                : PingStatus.valueOf(name.toUpperCase());
    }

    @JsonValue
    public String getName() {
        return name;
    }

}

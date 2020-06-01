package ru.veretennikov.notificationsystem.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum PingStatus {

    UNAVAILABLE_SUBSCRIBER("unavailableSubscriber"),
    IN_NETWORK("inNetwork");

    private final String name;

    PingStatus(String name) {
        this.name = name;
    }

    @JsonCreator
    public static PingStatus fromString(String name) {
        return name == null ? null :Stream.of(values())
                .filter(pingStatus -> pingStatus.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getName() {
        return name;
    }

}

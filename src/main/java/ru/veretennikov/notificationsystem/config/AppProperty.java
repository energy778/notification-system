package ru.veretennikov.notificationsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("application")
public class AppProperty {
    private String unavailableUrlPrefix;
    private String pingUrl;
    private String notifyUrl;
    private int pingSleepInMinutes;
    private String defaultNotifyLanguage;
    private String defaultNotifyCountry;
}

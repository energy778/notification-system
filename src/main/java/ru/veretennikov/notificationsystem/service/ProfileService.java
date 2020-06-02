package ru.veretennikov.notificationsystem.service;

import reactor.core.publisher.Flux;
import ru.veretennikov.notificationsystem.domain.Profile;

public interface ProfileService {
    Flux<Profile> getProfileByPhoneNumber(String phoneNumber);
    Flux<Profile> getAllProfiles();
}

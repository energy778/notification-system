package ru.veretennikov.notificationsystem.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.veretennikov.notificationsystem.domain.Profile;
import ru.veretennikov.notificationsystem.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;

    public ProfileServiceImpl(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Profile> getProfileByPhoneNumber(String phoneNumber) {
        return repository.findAllByPhoneNumberLike(phoneNumber);
    }

    @Override
    public Flux<Profile> getAllProfiles() {
        return repository.findAll();
    }

}

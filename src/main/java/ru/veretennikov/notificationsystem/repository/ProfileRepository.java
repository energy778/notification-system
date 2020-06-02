//package ru.veretennikov.notificationsystem.repository;
//
//import org.springframework.data.r2dbc.repository.Query;
//import org.springframework.data.repository.reactive.ReactiveCrudRepository;
//import reactor.core.publisher.Flux;
//import ru.veretennikov.notificationsystem.domain.Profile;
//
//public interface ProfileRepository extends ReactiveCrudRepository<Profile, Long> {
//
////    @Query("SELECT * FROM profiles WHERE phone_number = :phoneNumber")
//    Flux<Profile> findAllByPhoneNumberLike(String phoneNumber);
//
//}

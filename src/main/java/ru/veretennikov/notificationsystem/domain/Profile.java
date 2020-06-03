package ru.veretennikov.notificationsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private Long id;
    private String phoneNumber;
    private String lang;
    private String country;
    private String timeZone;

    public Profile(String phoneNumber, String lang, String country, String timeZone) {
        this.phoneNumber = phoneNumber;
        this.lang = lang;
        this.country = country;
        this.timeZone = timeZone;
    }

}

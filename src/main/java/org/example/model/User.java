package org.example.model;

import java.time.LocalDate;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private String nickname;
    private LocalDate birthday;
    private String password;
}

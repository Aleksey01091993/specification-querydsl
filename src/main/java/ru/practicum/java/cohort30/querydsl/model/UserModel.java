package ru.practicum.java.cohort30.querydsl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserModel {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private int age;
    private Instant created;
}

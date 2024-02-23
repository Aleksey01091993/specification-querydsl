package ru.practicum.java.cohort30.querydsl.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserFilterRequest {

    private String search;

    private String email;

    private Integer ageMin;

    private Integer ageMax;

    @NotNull
    @Min(0)
    Integer page;

    @NotNull
    @Min(1)
    @Max(100)
    Integer size;

    @NotNull
    String sortBy;
}

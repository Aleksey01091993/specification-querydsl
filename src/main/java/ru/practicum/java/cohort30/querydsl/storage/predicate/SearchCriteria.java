package ru.practicum.java.cohort30.querydsl.storage.predicate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}

package ru.practicum.java.cohort30.querydsl.storage.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPredicatesBuilder {

    private List<SearchCriteria> params;

    public UserPredicatesBuilder() {
        params = new ArrayList<>();
    }

    public UserPredicatesBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public BooleanExpression build() {
        if (params.isEmpty()) {
            return null;
        }

        List<BooleanExpression> predicates = params.stream()
                .map(param -> {
                    UserPredicate predicate = new UserPredicate(param);
                    return predicate.getPredicate();
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}

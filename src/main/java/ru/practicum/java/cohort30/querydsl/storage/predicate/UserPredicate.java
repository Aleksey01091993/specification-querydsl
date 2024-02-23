package ru.practicum.java.cohort30.querydsl.storage.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import lombok.AllArgsConstructor;
import org.hibernate.query.criteria.internal.ValueHandlerFactory;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;

@AllArgsConstructor
public class UserPredicate {

    private SearchCriteria criteria;

    public BooleanExpression getPredicate() {
        PathBuilder<UserEntity> entityPath = new PathBuilder<>(UserEntity.class, "userEntity");

        if (ValueHandlerFactory.isNumeric(criteria.getValue())) {
            NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case "=":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
            }
        } else {
            StringPath path = entityPath.getString(criteria.getKey());
            switch (criteria.getOperation()) {
                case "=":
                    return path.containsIgnoreCase(criteria.getValue().toString());
                case "~":
                    return path.like('%' + criteria.getValue().toString().trim() + '%');
            }
        }
        return null;
    }
}

package ru.practicum.java.cohort30.querydsl.storage.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;

import java.util.Objects;

@UtilityClass
public class UserSpecs {

    public static final int SEARCH_MIN_LENGTH = 2;

    public static Specification<UserEntity> searchByFio(String search) {
        if (!StringUtils.hasLength(search)) {
            return null;
        }
        return Specification.where(like(search, "firstName"))
                .or(like(search, "middleName"))
                .or(like(search, "lastName"));
    }

    public static Specification<UserEntity> hasEmail(String email) {
        if (!StringUtils.hasLength(email)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email));
    }

    public static Specification<UserEntity> isAgeGreaterThanOrEqualTo(Integer age) {
        if (Objects.isNull(age)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("age"), age));
    }

    public static Specification<UserEntity> isAgeLessThan(Integer age) {
        if (Objects.isNull(age)) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("age"), age));
    }

    private static Specification<UserEntity> like(String searchExpression, String searchField) {
        String expression = toLikeExpression(searchExpression);
        if (Objects.isNull(expression)) {
            return null;
        }
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(searchField)), expression));
    }

    public static String toLikeExpression(String query) {
        if (!StringUtils.hasText(query)) {
            return null;
        }

        String modified = query.trim().replaceAll(" ", "").toLowerCase();
        if (modified.length() < SEARCH_MIN_LENGTH) {
            return null;
        }
        return '%' + modified + '%';
    }
}

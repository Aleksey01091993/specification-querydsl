package ru.practicum.java.cohort30.querydsl.storage.specification;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.java.cohort30.querydsl.model.UserFilterModel;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
public class UserSpecification implements Specification<UserEntity> {

    private UserFilterModel filter;

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        getEmailPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getAgeGreaterThanOrEqualToPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getAgeLessThanPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        getSearchPredicate(root, criteriaBuilder).ifPresent(predicates::add);

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Optional<Predicate> getEmailPredicate(Root<UserEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getEmail())
                .map(email -> criteriaBuilder.equal(root.get("email"), email));
    }

    private Optional<Predicate> getAgeGreaterThanOrEqualToPredicate(Root<UserEntity> root,
                                                                    CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getAgeMin())
                .map(age -> criteriaBuilder.greaterThanOrEqualTo(root.get("age"), age));
    }

    private Optional<Predicate> getAgeLessThanPredicate(Root<UserEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getAgeMax())
                .map(age -> criteriaBuilder.lessThan(root.get("age"), age));
    }

    private Optional<Predicate> getSearchPredicate(Root<UserEntity> root,
                                                   CriteriaBuilder criteriaBuilder) {
        String expression = UserSpecs.toLikeExpression(filter.getSearch());
        if (Objects.isNull(expression)) {
            return Optional.empty();
        }
        return Optional.ofNullable(filter.getSearch())
                .filter(search -> search.length() >= UserSpecs.SEARCH_MIN_LENGTH)
                .map(search -> criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), expression),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("middleName")), expression),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), expression)
                ));
    }
}

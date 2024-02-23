package ru.practicum.java.cohort30.querydsl.storage;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.java.cohort30.querydsl.mapper.UserMapper;
import ru.practicum.java.cohort30.querydsl.model.UserFilterModel;
import ru.practicum.java.cohort30.querydsl.model.UserModel;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;
import ru.practicum.java.cohort30.querydsl.storage.predicate.UserPredicatesBuilder;
import ru.practicum.java.cohort30.querydsl.storage.repository.UserRepository;
import ru.practicum.java.cohort30.querydsl.storage.specification.UserSpecification;
import ru.practicum.java.cohort30.querydsl.storage.specification.UserSpecs;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorage {

    private final UserRepository repository;
    private final UserMapper mapper;

    @SuppressWarnings("UnusedReturnValue")
    public UserModel save(UserModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    public Page<UserModel> search(UserFilterModel filter, Pageable pageable) {
        Specification<UserEntity> specification = Specification
                .where(UserSpecs.hasEmail(filter.getEmail()))
                .and(UserSpecs.searchByFio(filter.getSearch()))
                .and(UserSpecs.isAgeGreaterThanOrEqualTo(filter.getAgeMin()))
                .and(UserSpecs.isAgeLessThan(filter.getAgeMax()));

        return repository.findAll(specification, pageable)
                .map(mapper::toModel);
    }

    public Page<UserModel> searchV2(UserFilterModel filter, Pageable pageable) {
        return repository.findAll(new UserSpecification(filter), pageable)
                .map(mapper::toModel);
    }

    public Page<UserModel> searchV3(UserFilterModel filter, Pageable pageable) {
        UserPredicatesBuilder builder = new UserPredicatesBuilder();
        Optional.ofNullable(filter.getEmail()).ifPresent(email -> builder.with("email", "=", email));
        Optional.ofNullable(filter.getAgeMin()).ifPresent(age -> builder.with("age", ">", age));
        Optional.ofNullable(filter.getAgeMax()).ifPresent(age -> builder.with("age", "<", age));
        Optional.ofNullable(filter.getSearch()).ifPresent(search -> builder.with("lastName", "~", search));
        BooleanExpression expression = builder.build();

        return repository.findAll(expression, pageable)
                .map(mapper::toModel);
    }
}

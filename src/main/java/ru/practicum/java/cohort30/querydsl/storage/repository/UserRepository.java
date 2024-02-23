package ru.practicum.java.cohort30.querydsl.storage.repository;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Repository;
import ru.practicum.java.cohort30.querydsl.storage.entity.QUserEntity;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>,
        JpaSpecificationExecutor<UserEntity>,
        QuerydslPredicateExecutor<UserEntity>, QuerydslBinderCustomizer<QUserEntity> {

    @Override
    default void customize(final QuerydslBindings bindings, final QUserEntity root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        bindings.excluding(root.email);
    }
}

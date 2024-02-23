package ru.practicum.java.cohort30.querydsl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.java.cohort30.querydsl.model.UserFilterModel;
import ru.practicum.java.cohort30.querydsl.model.UserModel;
import ru.practicum.java.cohort30.querydsl.storage.UserStorage;

import java.time.Instant;

@SpringBootTest
public class UserTest {

    public static final Pageable PAGEABLE = PageRequest.of(0, 20, Sort.by("created"));

    @Autowired
    private UserStorage storage;

    private UserModel first;
    private UserModel second;

    @BeforeEach
    public void init() {
        first = UserModel.builder()
                .id(1L)
                .firstName("John")
                .middleName("Maria")
                .lastName("Doe")
                .email("john@doe.com")
                .age(22)
                .created(Instant.now().minusSeconds(60))
                .build();
        storage.save(first);

        second = UserModel.builder()
                .id(2L)
                .firstName("Tom")
                .middleName("Victoria")
                .lastName("Doe")
                .email("tom@doe.com")
                .age(26)
                .created(Instant.now().minusSeconds(30))
                .build();
        storage.save(second);
    }

    // Create Specification by where

    @Test
    public void findByEmail() {
        UserFilterModel filter = UserFilterModel.builder()
                .email("john@doe.com")
                .build();

        Page<UserModel> users = storage.search(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), first.getLastName());
    }

    @Test
    public void findByAge() {
        UserFilterModel filter = UserFilterModel.builder()
                .ageMin(20)
                .ageMax(25)
                .build();

        Page<UserModel> users = storage.search(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), first.getLastName());
    }

    @Test
    public void findBySearch() {
        UserFilterModel filter = UserFilterModel.builder()
                .search("Vict")
                .build();

        Page<UserModel> users = storage.search(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), second.getLastName());
    }

    @Test
    public void findAllBySearch() {
        UserFilterModel filter = UserFilterModel.builder()
                .search("Doe")
                .build();

        Page<UserModel> users = storage.search(filter, PAGEABLE);

        Assertions.assertEquals(2, users.getContent().size());
    }

    // Successor of Specification

    @Test
    public void findByEmailV2() {
        UserFilterModel filter = UserFilterModel.builder()
                .email("john@doe.com")
                .build();

        Page<UserModel> users = storage.searchV2(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), first.getLastName());
    }

    @Test
    public void findByAgeV2() {
        UserFilterModel filter = UserFilterModel.builder()
                .ageMin(20)
                .ageMax(25)
                .build();

        Page<UserModel> users = storage.searchV2(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), first.getLastName());
    }

    @Test
    public void findBySearchV2() {
        UserFilterModel filter = UserFilterModel.builder()
                .search("Vict")
                .build();

        Page<UserModel> users = storage.searchV2(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), second.getLastName());
    }

    @Test
    public void findAllBySearchV2() {
        UserFilterModel filter = UserFilterModel.builder()
                .search("Doe")
                .build();

        Page<UserModel> users = storage.searchV2(filter, PAGEABLE);

        Assertions.assertEquals(2, users.getContent().size());
    }

    // Query DSL

    @Test
    public void findByEmailV3() {
        UserFilterModel filter = UserFilterModel.builder()
                .email("tom@doe.com")
                .build();

        Page<UserModel> users = storage.searchV3(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), second.getLastName());
    }

    @Test
    public void findByAgeV3() {
        UserFilterModel filter = UserFilterModel.builder()
                .ageMin(20)
                .ageMax(25)
                .build();

        Page<UserModel> users = storage.searchV3(filter, PAGEABLE);

        Assertions.assertEquals(1, users.getContent().size());
        Assertions.assertEquals(users.getContent().get(0).getLastName(), first.getLastName());
    }

    @Test
    public void findAllBySearchV3() {
        UserFilterModel filter = UserFilterModel.builder()
                .search("Do")
                .build();

        Page<UserModel> users = storage.searchV3(filter, PAGEABLE);

        Assertions.assertEquals(2, users.getContent().size());
    }
}

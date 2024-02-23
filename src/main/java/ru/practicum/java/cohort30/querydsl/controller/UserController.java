package ru.practicum.java.cohort30.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.java.cohort30.querydsl.mapper.UserMapper;
import ru.practicum.java.cohort30.querydsl.model.UserFilterRequest;
import ru.practicum.java.cohort30.querydsl.model.UserResponse;
import ru.practicum.java.cohort30.querydsl.storage.UserStorage;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserStorage service;
    private final UserMapper mapper;

    @PostMapping("/filter")
    public Page<UserResponse> filter(@RequestBody UserFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy()));

        return service.search(mapper.toModel(request), pageable)
                .map(mapper::toResponse);
    }
}

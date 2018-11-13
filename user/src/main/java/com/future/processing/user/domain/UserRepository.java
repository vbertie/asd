package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.UserDto;
import com.future.processing.user.domain.model.User;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UserRepository extends Repository<User, Long> {

    void save(User user);

    Optional<UserDto> findOptionalById(Long id);

    Optional<UserDto> findOptionalByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByUserDataEmail(String email);
}

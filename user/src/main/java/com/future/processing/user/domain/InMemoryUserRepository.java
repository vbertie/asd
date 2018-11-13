package com.future.processing.user.domain;

import com.future.processing.user.domain.dto.UserDto;
import com.future.processing.user.domain.model.User;
import com.future.processing.user.domain.model.User.UserData;
import com.future.processing.user.domain.model.Wallet;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryUserRepository implements UserRepository {

    private Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Optional<UserDto> findOptionalById(Long id) {
        return Optional.ofNullable(createUserDto(users.get(id)));
    }

    @Override
    public Optional<UserDto> findOptionalByUsername(String username) {
        return users.values()
                .stream()
                .filter(it -> it.getUsername().equals(username))
                .map(this::createUserDto)
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users.values()
                .stream()
                .filter(it -> it.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUserDataEmail(String email) {
        return users.values()
                .stream()
                .filter(it -> it.getUserData().getEmail().equals(email))
                .findFirst();
    }

    UserDto createUserDto(User user) {

        if (user == null) {
            return null;
        }

        return new UserDto() {
            @Override
            public long getId() {
                return user.getId();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public UserData getUserData() {
                return user.getUserData();
            }

            @Override
            public Wallet getWallet() {
                return user.getWallet();
            }
        };
    }
}

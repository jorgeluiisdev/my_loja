package com.custom.app.core.port;

import com.custom.app.core.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User update(User user);
    void delete(UUID existingUserId);
    List<User> findAll();
    Optional<User> findById(UUID userId);
    Optional<User> findByLogin(String login);
    Optional<User> findByFirstName(String firstName);
    Optional<User> findByLastName(String lastName);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
}
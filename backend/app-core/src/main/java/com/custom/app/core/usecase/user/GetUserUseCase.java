package com.custom.app.core.usecase.user;

import com.custom.app.core.model.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {
    Optional<User> findUserById(UUID userId);
    Optional<User> findUserByLogin(String login);
}
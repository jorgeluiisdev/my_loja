package com.custom.app.core.usecase.user;

import com.custom.app.core.model.User;

import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {
    Optional<User> execute(UUID userId);
}
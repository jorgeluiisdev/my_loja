package com.custom.app.core.usecase.user;

import com.custom.app.core.model.User;

import java.util.UUID;

public interface UpdateUserUseCase {
    User updateUser(UUID userId, User user);
}
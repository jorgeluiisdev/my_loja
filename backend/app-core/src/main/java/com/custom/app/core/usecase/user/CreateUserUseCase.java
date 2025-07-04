package com.custom.app.core.usecase.user;

import com.custom.app.core.model.User;

public interface CreateUserUseCase {
    User execute(User user);
}
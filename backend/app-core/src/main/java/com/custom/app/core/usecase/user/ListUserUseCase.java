package com.custom.app.core.usecase.user;

import com.custom.app.core.model.User;

import java.util.List;

public interface ListUserUseCase {
    List<User> execute();
}
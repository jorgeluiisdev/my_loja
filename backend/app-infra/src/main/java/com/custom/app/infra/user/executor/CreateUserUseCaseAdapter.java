package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.CreateUserUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseAdapter implements CreateUserUseCase {

    @Override
    public User execute(User user) {
        return null;
    }
}
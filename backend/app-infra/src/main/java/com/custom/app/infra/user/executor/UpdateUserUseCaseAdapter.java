package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.UpdateUserUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateUserUseCaseAdapter implements UpdateUserUseCase {

    @Override
    public User execute(User user) {
        return null;
    }
}
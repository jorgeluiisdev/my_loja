package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.GetUserUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserUseCaseAdapter implements GetUserUseCase {

    @Override
    public Optional<User> execute(UUID userId) {
        return Optional.empty();
    }
}
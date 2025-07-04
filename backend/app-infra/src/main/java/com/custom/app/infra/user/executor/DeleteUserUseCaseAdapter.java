package com.custom.app.infra.user.executor;

import com.custom.app.core.usecase.user.DeleteUserUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteUserUseCaseAdapter implements DeleteUserUseCase {

    @Override
    public void execute(UUID userId) {

    }
}
package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.usecase.user.ListUserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUserUseCaseAdapter implements ListUserUseCase {

    @Override
    public List<User> execute() {
        return List.of();
    }
}
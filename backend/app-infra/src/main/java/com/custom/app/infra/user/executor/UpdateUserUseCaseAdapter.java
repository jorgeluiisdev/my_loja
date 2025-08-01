package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.port.UserRepository;
import com.custom.app.core.usecase.user.UpdateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class UpdateUserUseCaseAdapter implements UpdateUserUseCase {

    private final UserRepository userRepository;

    @Autowired
    public UpdateUserUseCaseAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(UUID userId, User user) {
        return this.userRepository.update(userId, user);
    }
}
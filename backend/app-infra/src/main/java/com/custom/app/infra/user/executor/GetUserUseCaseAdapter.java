package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.port.UserRepository;
import com.custom.app.core.usecase.user.GetUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetUserUseCaseAdapter implements GetUserUseCase {

    private final UserRepository userRepository;

    @Autowired
    public GetUserUseCaseAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(UUID userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return this.userRepository.findByLogin(login);
    }
}
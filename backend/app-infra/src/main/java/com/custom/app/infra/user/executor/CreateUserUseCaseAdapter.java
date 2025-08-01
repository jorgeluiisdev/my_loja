package com.custom.app.infra.user.executor;

import com.custom.app.core.model.User;
import com.custom.app.core.port.UserRepository;
import com.custom.app.core.usecase.user.CreateUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCaseAdapter implements CreateUserUseCase {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserUseCaseAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }
}
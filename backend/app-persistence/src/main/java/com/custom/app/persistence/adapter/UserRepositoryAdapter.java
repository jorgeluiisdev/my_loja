package com.custom.app.persistence.adapter;

import com.custom.app.core.model.User;
import com.custom.app.core.port.UserRepository;
import com.custom.app.persistence.entity.UserEntity;
import com.custom.app.persistence.mapepr.UserMapper;
import com.custom.app.persistence.repository.UserJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Autowired
    public UserRepositoryAdapter(
            UserJpaRepository userRepository,
            UserMapper userMapper
    ) {
        this.jpaRepository = userRepository;
        this.mapper = userMapper;
    }

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Novo objeto não deve conter ID");
        }
        UserEntity entity = this.mapper.toEntity(user);
        UserEntity savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public User update(UUID existingUserId, User user) {
        if (existingUserId == null || !existingUserId.equals(user.getId())) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        UserEntity entity = this.mapper.toEntity(user);
        UserEntity savedEntity = this.jpaRepository.save(entity);
        return this.mapper.toDomain(savedEntity);
    }

    @Override
    public void delete(UUID existingUserId) {
        if (existingUserId == null) {
            throw new EntityNotFoundException("O Id do usuário não pode ser nulo");
        }
        this.jpaRepository.deleteById(existingUserId);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = this.jpaRepository.findAll();
        return entities.stream()
                .map(this.mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<User> findById(UUID userId) {
        return this.jpaRepository.findById(userId)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return this.jpaRepository.findByLogin(login)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<User> findByFirstName(String firstName) {
        return this.jpaRepository.findByFirstName(firstName)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<User> findByLastName(String lastName) {
        return this.jpaRepository.findByLastName(lastName)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaRepository.findByEmail(email)
                .map(this.mapper::toDomain);
    }

    @Override
    public Optional<User> findByPhone(String phone) {
        return this.jpaRepository.findByPhone(phone)
                .map(this.mapper::toDomain);
    }
}
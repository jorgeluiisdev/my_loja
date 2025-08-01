package com.custom.app.persistence.repository;

import com.custom.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByLogin(String login);
    Optional<UserEntity> findByFirstName(String firstName);
    Optional<UserEntity> findByLastName(String lastName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
}
package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.User;
import com.custom.app.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setLogin(user.getLogin());
        userEntity.setPassword(user.getPassword());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setImageUrl(user.getImageUrl());

        return userEntity;
    }

    public User toModel(UserEntity userEntity) {
        User user = new User();

        user.setLogin(userEntity.getLogin());
        user.setPassword(userEntity.getPassword());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setImageUrl(userEntity.getImageUrl());

        return user;
    }
}
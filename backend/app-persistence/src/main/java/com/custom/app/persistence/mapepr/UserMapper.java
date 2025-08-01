package com.custom.app.persistence.mapepr;

import com.custom.app.core.model.Image;
import com.custom.app.core.model.User;
import com.custom.app.persistence.entity.ImageEntity;
import com.custom.app.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ImageMapper mapper;

    @Autowired
    public UserMapper(ImageMapper mapper) {
        this.mapper = mapper;
    }

    public UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();

        if (user.getId() != null) {
            userEntity.setId(user.getId());
        }
        userEntity.setLogin(user.getLogin());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhone(user.getPhone());
        userEntity.setRefreshTokenHash(user.getRefreshTokenHash());

        Image image = new Image();
        image.setUserImageId(user.getUserImageId());

        ImageEntity imageEntity = this.mapper.toEntity(image);

        userEntity.setUserImageId(imageEntity.getUserImageId());

        return userEntity;
    }

    public User toDomain(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setLogin(userEntity.getLogin());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRole());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setRefreshTokenHash(userEntity.getRefreshTokenHash());

        ImageEntity imageEntity = new ImageEntity();

        imageEntity.setUserImageId(userEntity.getUserImageId());

        Image image = this.mapper.toDomain(imageEntity);

        user.setUserImageId(image.getUserImageId());

        return user;
    }
}
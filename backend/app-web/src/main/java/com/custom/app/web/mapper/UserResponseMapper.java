package com.custom.app.web.mapper;

import com.custom.app.core.model.User;
import com.custom.app.web.dto.response.LoginResponse;

public class UserResponseMapper {

    public static LoginResponse toLoginResponse(User user, String accessToken) {
        LoginResponse dto = new LoginResponse();

        dto.setUserId(user.getId());
        dto.setUsername(user.getFirstName() + " " + user.getLastName());
        dto.setUserRole(user.getRole());
        dto.setAccessToken(accessToken);
        dto.setUserId(user.getUserImageId());

        return dto;
    }
}
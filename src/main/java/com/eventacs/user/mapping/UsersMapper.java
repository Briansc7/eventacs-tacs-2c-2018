package com.eventacs.user.mapping;

import com.eventacs.user.dto.UserInfoDTO;
import com.eventacs.user.model.User;

public class UsersMapper {

    public UserInfoDTO fromModelToApi(User user) {
        return new UserInfoDTO(user.getId(), user.getName(), user.getLastName(), user.getEvents());
    }

}

package com.petcove.userservice.service;

import com.petcove.userservice.dto.UserCreateRequest;
import com.petcove.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserCreateRequest userCreateRequest) ;

    UserDto getUserInfo(Long id);

    UserDto updateUserInfo(Long id, UserCreateRequest  userCreateRequest);

    void deleteUser (Long id);
}

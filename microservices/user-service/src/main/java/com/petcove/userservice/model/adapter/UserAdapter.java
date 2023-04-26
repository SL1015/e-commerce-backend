package com.petcove.userservice.model.adapter;

import com.petcove.userservice.dto.UserCreateRequest;
import com.petcove.userservice.dto.UserDto;
import com.petcove.userservice.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAdapter {
    public static User RequestToEntity(UserCreateRequest userCreateRequest) {
        log.info("Converting customerAddRequest to customerEntity: {}", userCreateRequest);
        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());
        user.setPhone(userCreateRequest.getPhone());
        user.setAddress(userCreateRequest.getAddresses());
        user.setUsertype(userCreateRequest.getUsertype());
        return user;
    }

    public static UserDto EntityToDto(User user) {
        log.info("Converting customerEntity to customerDto: {}", user);
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .address(user.getAddress())
                .usertype(user.getUsertype())
                .build();
    }

}

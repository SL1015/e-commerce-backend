package com.petcove.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCreateRequest {
    @NotBlank(message = "Full name is mandatory")
    private String name;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @NotEmpty(message = "Addresses is mandatory")
    private List<String> addresses;

    @NotBlank(message = "User type is mandatory")
    private String usertype;

}

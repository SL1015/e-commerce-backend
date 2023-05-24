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
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Phone number is required.")
    private String phone;

    @NotEmpty(message = "Addresses is required.")
    private List<String> addresses;

}

package com.petcove.userservice.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String address;

}

package com.petcove.userservice.controller;

import com.petcove.userservice.dto.UserCreateRequest;
import com.petcove.userservice.dto.UserDto;
import com.petcove.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createCustomer(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        log.debug("Request received to create customer: {}", userCreateRequest);
        return new ResponseEntity<>(userService.createUser(userCreateRequest),  HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getCustomer(@PathVariable Long id) {
        log.debug("Request received to get customer with id: {}", id);
        return new ResponseEntity<>(userService.getUserInfo(id),  HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateCustomer(@PathVariable Long id, @Valid @RequestBody UserCreateRequest userCreateRequest) {
        log.debug("Request received to update customer with id: {}", id);
        return new ResponseEntity<>(userService.updateUserInfo(id, userCreateRequest),  HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("Request received to delete customer with id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
package com.petcove.userservice.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("User does not exist.");
    }
}

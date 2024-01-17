package ru.musify.userservice.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String userName;
    private String email;
    private String password;
}

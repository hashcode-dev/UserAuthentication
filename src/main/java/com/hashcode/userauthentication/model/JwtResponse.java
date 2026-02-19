package com.hashcode.userauthentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String userId;
    private String username;
    private String email;
    private String tokenType;
    private List<String> roles;
}

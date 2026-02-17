package com.hashcode.userauthentication.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserCredentialsService {
    public UserDetails findByUserName(String username);
}

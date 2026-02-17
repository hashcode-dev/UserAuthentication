package com.hashcode.userauthentication.service;

import com.hashcode.userauthentication.model.UserCredentials;

public interface UserCredentialsService {
    public UserCredentials findByUserName(String username);
}

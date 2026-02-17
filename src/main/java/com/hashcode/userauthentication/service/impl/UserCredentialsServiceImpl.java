package com.hashcode.userauthentication.service.impl;

import com.hashcode.userauthentication.model.UserCredentials;
import com.hashcode.userauthentication.repository.UserCredentialsRepository;
import com.hashcode.userauthentication.service.UserCredentialsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public  UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public UserCredentials findByUserName(String username) {
        return getUserCredentialsRepository().findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}

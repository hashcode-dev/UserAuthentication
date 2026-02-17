package com.hashcode.userauthentication.service.impl;

import com.hashcode.userauthentication.model.UserCredentials;
import com.hashcode.userauthentication.repository.UserCredentialsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    public  UserDetailsServiceImpl(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserCredentials userCredentials = getUserCredentialsRepository().findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();

    }
}

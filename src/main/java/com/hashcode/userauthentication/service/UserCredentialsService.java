package com.hashcode.userauthentication.service;

import com.hashcode.userauthentication.model.JwtResponse;
import com.hashcode.userauthentication.model.ResponseMessage;
import com.hashcode.userauthentication.model.UserCredentials;
import com.hashcode.userauthentication.model.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserCredentialsService {
    public UserDetails findByUserName(String username);
    public JwtResponse login(UserCredentials userCredentials);
    public ResponseEntity<?> signUp(UserCredentials userCredentials);
    public ResponseEntity<?> getAllUserRoles();
    public ResponseEntity<?> createUserRoles(UserRole userRole);
}

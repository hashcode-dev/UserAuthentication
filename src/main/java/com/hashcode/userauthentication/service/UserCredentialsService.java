package com.hashcode.userauthentication.service;

import com.hashcode.userauthentication.model.JwtResponse;
import com.hashcode.userauthentication.model.ResponseMessage;
import com.hashcode.userauthentication.model.UserCredentials;
import com.hashcode.userauthentication.model.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserCredentialsService {
    UserDetails findByUserName(String username);
    JwtResponse login(UserCredentials userCredentials);
    ResponseEntity<?> signUp(UserCredentials userCredentials);
    ResponseEntity<?> getAllUserRoles();
    ResponseEntity<?> createUserRoles(UserRole userRole);
    ResponseEntity<?> isTokenValid(String token);
}

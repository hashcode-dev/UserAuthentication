package com.hashcode.userauthentication.controller;

import com.hashcode.userauthentication.model.*;
import com.hashcode.userauthentication.service.UserCredentialsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Getter
public class AuthController {

    UserCredentialsService userCredentialsService;

    @Autowired
    public  AuthController(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserCredentials userCredentials) {
        return ResponseEntity.ok(getUserCredentialsService().login(userCredentials));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserCredentials userCredentials) {
        return getUserCredentialsService().signUp(userCredentials);
    }

    @GetMapping("/getAllUserRoles")
    public ResponseEntity<?> getAllUserRoles() {
        return getUserCredentialsService().getAllUserRoles();
    }

    @PostMapping("/createUserRoles")
    public ResponseEntity<?> createUserRoles(@RequestBody UserRole userRole) {
        return getUserCredentialsService().createUserRoles(userRole);
    }
}

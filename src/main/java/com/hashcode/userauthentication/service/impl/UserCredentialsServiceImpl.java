package com.hashcode.userauthentication.service.impl;

import com.hashcode.userauthentication.configuration.JWTUtil;
import com.hashcode.userauthentication.model.*;
import com.hashcode.userauthentication.repository.UserCredentialsRepository;
import com.hashcode.userauthentication.repository.UserRoleRepository;
import com.hashcode.userauthentication.service.UserCredentialsService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Getter
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private UserCredentialsRepository userCredentialsRepository;
    AuthenticationManager authenticationManager;
    UserCredentialsRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder encoder;
    JWTUtil jwtUtil;

    @Autowired
    public  UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository, JWTUtil jwtUtil ,AuthenticationManager authenticationManager , UserCredentialsRepository userRepository , UserRoleRepository userRoleRepository , PasswordEncoder encoder) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails findByUserName(String username) {
        UserCredentials userCredentials  = getUserCredentialsRepository().findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles(userCredentials.getRoles().toArray(String[]::new))
                .build();
    }

    @Override
    public JwtResponse login(UserCredentials userCredentials) {

        UserCredentials userCred = getUserCredentialsRepository().findByUsername(userCredentials.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userCredentials.getUsername()));
        Authentication authentication = getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = getJwtUtil().generateToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponse(jwt,
                userCred.getId(),
                userCred.getUsername(),
                userCred.getEmail(),
                "Bearer",
                roles);
    }

    @Override
    public ResponseEntity<?> signUp(UserCredentials userCredentials) {

        // Check if username already exists
        if (getUserRepository().existsByUsername(userCredentials.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Username is already taken!"));
        }

        // Check if email already exists
        if (StringUtils.hasText(userCredentials.getEmail()) && getUserRepository().existsByEmail(userCredentials.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Email is already in use!"));
        }

        // Create new user account
        UserCredentials user = new UserCredentials();
        user.setUsername(userCredentials.getUsername());
        user.setEmail(userCredentials.getEmail());
        user.setPassword(getEncoder().encode(userCredentials.getPassword()));

        Set<String> userRoles = userCredentials.getRoles();
        Set<String> roles = new HashSet<>();

        if (userRoles == null || userRoles.isEmpty()) {;
            UserRole userRole = getUserRoleRepository().findByRole(EnumRole.USER.toString())
                    .orElseThrow(() -> new RuntimeException("Error: " + EnumRole.USER + " Role not found."));
            roles.add(userRole.getRole());
        } else {
            userRoles.forEach(role -> {
                UserRole userRole = getUserRoleRepository().findByRole(role)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " not found."));
                roles.add(userRole.getRole());
            });
        }

        user.setRoles(roles);
        getUserRepository().save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> getAllUserRoles() {
        return ResponseEntity.ok(getUserRoleRepository().findAll());
    }

    @Override
    public ResponseEntity<?> createUserRoles(UserRole userRole) {
        return ResponseEntity.ok(getUserRoleRepository().save(userRole));
    }
}

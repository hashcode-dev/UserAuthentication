package com.hashcode.userauthentication.repository;

import com.hashcode.userauthentication.model.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends MongoRepository<UserCredentials, String> {
    Optional<UserCredentials> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

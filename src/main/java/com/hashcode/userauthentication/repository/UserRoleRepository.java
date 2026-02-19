package com.hashcode.userauthentication.repository;

import com.hashcode.userauthentication.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends MongoRepository<UserRole, String> {

    @Query("{ 'role': ?0 }")
    Optional<UserRole> findByRole(String role);
}

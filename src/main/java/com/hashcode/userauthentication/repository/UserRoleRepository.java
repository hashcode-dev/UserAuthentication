package com.hashcode.userauthentication.repository;

import com.hashcode.userauthentication.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRoleRepository extends MongoRepository<UserRole, String> {
    Optional<UserRole> findByName(UserRole name);
}

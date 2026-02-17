package com.hashcode.userauthentication.repository;

import com.hashcode.userauthentication.model.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface UserRoleRepository extends MongoRepository<UserRole, String> {
    Optional<UserRole> findByName(UserRole name);
}

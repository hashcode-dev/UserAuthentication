package com.hashcode.userauthentication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document("user_credentials")
@NoArgsConstructor
public class UserCredentials {
    @Id
    private String userId;
    private String username;
    private String password;
    private String email;
    @DBRef
    private Set<EnumRole> roles = new HashSet<>();
}

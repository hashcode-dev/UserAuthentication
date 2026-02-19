package com.hashcode.userauthentication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("user_roles")
@NoArgsConstructor
public class UserRole {
    @Id
    private String id;
    @Field("role")
    private String role;
}

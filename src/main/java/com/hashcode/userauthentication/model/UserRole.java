package com.hashcode.userauthentication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("user_role")
@NoArgsConstructor
public class UserRole {
    @Id
    private String roleId;
    private EnumRole role;
}

package com.jaiveer.backend.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ROLE_USER("USER"),
    ROLE_MOD("MOD"),
    ROLE_ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}

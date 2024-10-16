package com.VTB.AnotherVault.Entities.Enums;

import org.springframework.security.core.GrantedAuthority;

public enum RepositoryRole implements GrantedAuthority {
    ROLE_ROOT, ROLE_REDACTOR, ROLE_VIEWERS;

    @Override
    public String getAuthority() {
        return name();
    }

}

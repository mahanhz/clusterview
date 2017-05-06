package org.amhzing.clusterview.infra.user;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.Validate.notBlank;

public enum UserRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN");

    private final String role;

    UserRole(final String role) {
        this.role = notBlank(role);
    }

    public String getRole() {
        return role;
    }

    public String getRoleWithoutPrefix() {
        return StringUtils.remove(role, "ROLE_");
    }
}

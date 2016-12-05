package org.amhzing.clusterview.user;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.Validate.notBlank;

public enum UserRole {

    SE_USER("ROLE_SE_USER"),
    SE_ADMIN("ROLE_SE_ADMIN"),
    SE_SUPER_ADMIN("ROLE_SUPER_ADMIN");

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

package com.pfizer.sacchonapi.security;

public enum Role {
    ROLE_NA("n/a"),
    ROLE_ADMIN("chiefDoctor"),
    ROLE_USER("patient"),
    ROLE_OWNER("doctor");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public static Role getRoleValue(String roleParameter) {
        for (Role role : Role.values()) {
            if (roleParameter.equals(role.getRoleName()))
                return role;
        }
        return ROLE_NA;
    }
}
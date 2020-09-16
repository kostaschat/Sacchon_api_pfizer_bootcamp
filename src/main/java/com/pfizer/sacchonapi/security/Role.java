package com.pfizer.sacchonapi.security;

public enum Role {
    ROLE_NA("n/a"),
    ROLE_CHIEF_DOCTOR("chiefDoctor"),
    ROLE_PATIENT("patient"),
    ROLE_DOCTOR("doctor");

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
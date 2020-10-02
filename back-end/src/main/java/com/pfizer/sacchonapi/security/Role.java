package com.pfizer.sacchonapi.security;


public enum Role {
    na("n/a"),
    chiefDoctor("chiefDoctor"),
    patient("patient"),
    doctor("doctor");

    @Override
    public String toString() {
        return  roleName.toLowerCase();
    }

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
        return na;
    }
}
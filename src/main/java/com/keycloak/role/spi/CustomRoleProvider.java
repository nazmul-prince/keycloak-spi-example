package com.keycloak.role.spi;

import org.keycloak.models.RoleModel;

import com.keycloak.role.spi.model.CompositeRoleModels;

import java.util.List;

public interface CustomRoleProvider {
    CompositeRoleModels getCompositeRolesWithAssignedRoles(String realmId);
}

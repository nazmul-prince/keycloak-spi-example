package com.keycloak.role.spi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CompositeRoleModels implements Serializable {
	private List<CompositeWithAssignedRoleModel> compositeRoles = new ArrayList<CompositeWithAssignedRoleModel>();
}

package com.keycloak.role.spi.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class AssignedRoleModel implements Serializable {
	private String id;
	private String name;
	private String description;
}

package com.keycloak.role.spi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;

import com.keycloak.role.spi.model.AssignedRoleModel;
import com.keycloak.role.spi.model.CompositeRoleModels;
import com.keycloak.role.spi.model.CompositeWithAssignedRoleModel;
import com.keycloak.role.spi.streams.AssignedRoleModelStream;
import com.keycloak.role.spi.streams.RoleModelStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRoleProviderImpl implements CustomRoleProvider {

	private final KeycloakSession session;

	public CustomRoleProviderImpl(KeycloakSession session) {
		this.session = session;
	}

	@Override
	public CompositeRoleModels getCompositeRolesWithAssignedRoles(String realmId) {
		log.info("Realm id in provider: " + realmId);
		RealmModel realm = session.realms().getRealm(realmId);
		log.info("RealmModel in provider: " + realm.toString());

		List<RoleModel> allRoles = realm.getRolesStream().toList();

		List<CompositeWithAssignedRoleModel> compositeWithAssignedRoleModels = getCompositeRolesWithAssignedRoles(allRoles);
		CompositeRoleModels compositeRoles = new CompositeRoleModels(compositeWithAssignedRoleModels);

		return compositeRoles;
	}
	
	private List<CompositeWithAssignedRoleModel> getCompositeRolesWithAssignedRoles(List<RoleModel> allRoles) {

		List<CompositeWithAssignedRoleModel> compositeWithAssignedRoleModels = new ArrayList<CompositeWithAssignedRoleModel>();
		
		RoleModelStream.of(allRoles)
		.allComposites()
		.removeDefaultRole()
		.forEach(roleModel -> {
			// Role is a composite role, add it to the result set
			log.info(
					"found composite role: " + roleModel.getName() + " is composite: " + roleModel.isComposite());

			CompositeWithAssignedRoleModel compositeWithAssignedRoleModel = buildCompositeWithAssignedRoleModel(
					roleModel);

			compositeWithAssignedRoleModels.add(compositeWithAssignedRoleModel);
			log.info("================================");
			log.info("================================");
		});
		
		return compositeWithAssignedRoleModels;
	}

	private CompositeWithAssignedRoleModel buildCompositeWithAssignedRoleModel(RoleModel role) {

//		Stream<RoleModel> childRolesStream = role.getCompositesStream();

		log.info("collecting all child roles");
		List<AssignedRoleModel> assignedRoles = AssignedRoleModelStream.of(
				RoleModelStream.of(role.getCompositesStream())
				.convertToAssignedRoleModel()
				).getList();
		
		log.info("printing all child roles");
		assignedRoles.forEach(cRole -> log.info("role name: " + cRole.getName()));

		CompositeWithAssignedRoleModel compositeWithAssignedRoleModel = CompositeWithAssignedRoleModel.builder()
				.id(role.getId()).name(role.getName()).description(role.getDescription()).assignedRoles(assignedRoles)
				.build();
		
		return compositeWithAssignedRoleModel;
	}
}

package com.keycloak.role.spi.streams;

import java.util.List;
import java.util.stream.Stream;

import org.keycloak.models.RoleModel;

import com.google.common.base.Objects;
import com.keycloak.role.spi.model.AssignedRoleModel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleModelStream implements ForwardingStream<RoleModel> {
	
	private final String DEFAULT_ROLE = "default-roles-test";
	
	private final Stream<RoleModel> stream;
	
	public static RoleModelStream of(List<RoleModel> roles) {
		return new RoleModelStream(roles.stream());
	}
	
	public static RoleModelStream of(Stream<RoleModel> stream) {
		return new RoleModelStream(stream);
	}
	
	public RoleModelStream allComposites() {
		return of(this.stream
				.filter(roleModel -> roleModel.isComposite()));
	}
	
	public RoleModelStream removeDefaultRole() {
		return of(this.stream
				.filter(roleModel -> !Objects.equal(roleModel.getName(), DEFAULT_ROLE)));
	}
	
	public RoleModelStream allCompositesWithoutDefaultRole() {
		return of(this.stream
				.filter(roleModel -> roleModel.isComposite() && !Objects.equal(roleModel.getName(), DEFAULT_ROLE)));
	}
	
	public AssignedRoleModelStream convertToAssignedRoleModel() {
		return AssignedRoleModelStream.of(this.stream
				.map(childRole -> AssignedRoleModel.builder()
						.id(childRole.getId())
						.name(childRole.getName())
						.description(childRole.getDescription())
						.build()));
	}

	@Override
	public Stream<RoleModel> getStream() {
		// TODO Auto-generated method stub
		return stream;
	}

}

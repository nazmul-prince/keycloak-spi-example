package com.keycloak.role.spi.streams;

import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Objects;
import com.keycloak.role.spi.model.AssignedRoleModel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignedRoleModelStream implements ForwardingStream<AssignedRoleModel> {
	
	private final String DEFAULT_ROLE = "default-roles-test";
	
	private final Stream<AssignedRoleModel> stream;
	
	public static AssignedRoleModelStream of(List<AssignedRoleModel> roles) {
		return new AssignedRoleModelStream(roles.stream());
	}
	
	public static AssignedRoleModelStream of(Stream<AssignedRoleModel> stream) {
		return new AssignedRoleModelStream(stream);
	}

	@Override
	public Stream<AssignedRoleModel> getStream() {
		// TODO Auto-generated method stub
		return stream;
	}

}

package com.keycloak.role.spi.resource;

import java.util.List;
import java.util.Map;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.services.resource.RealmResourceProvider;

import com.keycloak.role.spi.CustomRoleProvider;
import com.keycloak.role.spi.auth.Authenticator;
import com.keycloak.role.spi.model.CompositeRoleModels;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRoleResource implements RealmResourceProvider {

    private final RealmModel realm;
    private final CustomRoleProvider customRoleProvider;
    private final KeycloakSession keycloakSession;

    public CustomRoleResource(RealmModel realm,
    		CustomRoleProvider customRoleProvider,
    		KeycloakSession keycloakSession) {
        this.realm = realm;
        this.customRoleProvider = customRoleProvider;
        this.keycloakSession = keycloakSession;
    }

	@Override
	public void close() {
	}

	@Override
	public Object getResource() {
		return this;
	}

    @GET
    @Path("composite-with-assigned-roles")
    @Produces(MediaType.APPLICATION_JSON)
    public CompositeRoleModels getCompositeRolesWithChildren() {
    	Authenticator.INSTANCE.authenticate(keycloakSession);
        return customRoleProvider.getCompositeRolesWithAssignedRoles(realm.getId());
    }
    
    @GET
    @Path("composite-roles-demo")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getCompositeRolesWithChildrenTestDemo() {
//        return customRoleProvider.getCompositeRolesWithChildren(realm.getId());

        return Map.of("hello",
        		"test"
        		);
    }
    
    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> hello() {
//    	System.out.println("in hellooooooooooooooooooooooo: " + keycloakSession.getContext().getRealm().getName());
        return Map.of("hello",
        		"test"
        		);
    }
}

package com.keycloak.role.spi.resource;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

import com.keycloak.role.spi.CustomRoleProviderImpl;

public class CustomRoleResourceProviderFactory implements RealmResourceProviderFactory {
	
	public final static String COMPOSITE_ROLE_RESOURCE = "composite-role-resource";
	
    @Override
    public String getId() {
        return COMPOSITE_ROLE_RESOURCE;
    }

  @Override
  public RealmResourceProvider create(KeycloakSession session) {
      return new CustomRoleResource(session.getContext().getRealm(),
              new CustomRoleProviderImpl(session), session);
  }

	@Override
	public void init(Scope config) {
		
	}

	@Override
	public void postInit(KeycloakSessionFactory factory) {
		
	}

	@Override
	public void close() {
		
	}
}

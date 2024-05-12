package com.keycloak.role.spi;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.keycloak.role.spi.resource.CustomRoleResourceProviderFactory;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.Matchers.is;

@Slf4j
@Testcontainers
public class CustomRoleProviderImplTest {

	private final String realmUri = "/realms/test";

	@Container
	KeycloakContainer KEYCLOAK = new KeycloakContainer().withRealmImportFile("/test-realm.json")
			.withProviderClassesFrom("target/classes");

	public String getAccessToken() {
//		assertTrue(KEYCLOAK.isRunning());
		String authServerUrl = KEYCLOAK.getAuthServerUrl();
		log.info("auth server url: " + authServerUrl);

		String accessToken = given().contentType(ContentType.URLENC)
				.formParams(Map.of("username", "naz", "password", "123", "grant_type", "password", "client_id",
						"test-cli", "client_secret", "**********"))
				.post(authServerUrl + realmUri + "/protocol/openid-connect/token").then().assertThat().statusCode(200)
				.extract().path("access_token");

		log.info("access token: " + accessToken);
		return accessToken;
	}
//	@Test
	public void testKc() {
		assertTrue(KEYCLOAK.isRunning());
		String authServerUrl = KEYCLOAK.getAuthServerUrl();
		log.info("auth server url: " + authServerUrl);

		String accessToken = given().contentType(ContentType.URLENC)
				.formParams(Map.of("username", "naz", "password", "123", "grant_type", "password", "client_id",
						"test-cli", "client_secret", "**********"))
				.post(authServerUrl + realmUri + "/protocol/openid-connect/token").then().assertThat().statusCode(200)
				.extract().path("access_token");

		log.info("access token: " + accessToken);

	}

//	@Test
	public void testHelloResource() {
		assertTrue(KEYCLOAK.isRunning());
		String authServerUrl = KEYCLOAK.getAuthServerUrl();

		log.info("auth url: " + authServerUrl);
		String fullUrl = authServerUrl + realmUri + "/" + CustomRoleResourceProviderFactory.COMPOSITE_ROLE_RESOURCE
				+ "/hello";

		log.info("full url: " + fullUrl);

		String result = given()
			.get(fullUrl)
			.then()
//			.assertThat().statusCode(200)
			.extract()
			.asPrettyString();
////		

//		String result = givenSpec().when().get("composite-roles").then().statusCode(200).extract().asPrettyString();
////			.body("hello", is("test"))
//		;

		log.info("resultttttttttttttttt: " + result);
		log.info("Keycloak container logsssssssssssssss:");
		log.info(KEYCLOAK.getLogs());
	}
	
//	@Test
	public void testCompositeWithAssignedRolesResourceUnauthorized() {
		assertTrue(KEYCLOAK.isRunning());
		String authServerUrl = KEYCLOAK.getAuthServerUrl();

		log.info("auth url: " + authServerUrl);
		String fullUrl = authServerUrl + realmUri + "/" + CustomRoleResourceProviderFactory.COMPOSITE_ROLE_RESOURCE
				+ "/composite-with-assigned-roles";

		log.info("full url: " + fullUrl);

//		String result = given()
//			.get(fullUrl)
//			.then()
////			.assertThat().statusCode(200)
//			.extract()
//			.asPrettyString();
////		

//		String result = givenSpec()
//				.when()
//				.get("composite-with-assigned-roles")
//				.then()
//				.extract().asPrettyString();

		givenSpec()
				.when()
				.get("composite-with-assigned-roles")
				.then()
				.statusCode(401);
//		;

//		log.info("resultttttttttttttttt: " + result);
		log.info("Keycloak container logsssssssssssssss:");
		log.info(KEYCLOAK.getLogs());
	}
	
	@Test
	public void testCompositeWithAssignedRolesResourceAuthorized() {
		assertTrue(KEYCLOAK.isRunning());
		String authServerUrl = KEYCLOAK.getAuthServerUrl();

		System.out.println("auth url: " + authServerUrl + " version: " + KEYCLOAK.getKeycloakDefaultVersion());
		String fullUrl = authServerUrl + realmUri + "/" + CustomRoleResourceProviderFactory.COMPOSITE_ROLE_RESOURCE
				+ "/composite-with-assigned-roles";

		System.out.println("full url: " + fullUrl);

//		String accessToken = KEYCLOAK.getKeycloakAdminClient().tokenManager().getAccessToken().getToken();
		String accessToken = getAccessToken();
		log.info("Access token: " + accessToken);
//		String result = given()
//			.get(fullUrl)
//			.then()
////			.assertThat().statusCode(200)
//			.extract()
//			.asPrettyString();
////		

//		String result = givenSpec()
//				.when()
//				.get("composite-with-assigned-roles")
//				.then()
//				.extract().asPrettyString();

		String asPrettyString = givenSpec()
			.auth()
			.oauth2(accessToken)
			.when()
			.get("composite-with-assigned-roles")
			.then()
			.statusCode(200)
			.extract().asPrettyString();
//		;

		System.out.println("resultttttttttttttttt: " + asPrettyString);
		log.info("Keycloak container logsssssssssssssss:");
		log.info(KEYCLOAK.getLogs());
	}

	private RequestSpecification givenSpec() {
		String fullUrl = realmUri + "/" + CustomRoleResourceProviderFactory.COMPOSITE_ROLE_RESOURCE;

		log.info("full url: " + fullUrl);
		return given().baseUri(KEYCLOAK.getAuthServerUrl()).basePath(fullUrl);
	}
}

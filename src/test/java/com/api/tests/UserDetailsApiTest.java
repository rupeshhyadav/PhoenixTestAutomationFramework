package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsApiTest {
	
	@Test
	public void UserDetailsApiTest() {
		Header authHeader = new Header("Authorization",AuthTokenProvider.getToken(Role.FD));
		RestAssured.given()
						.baseUri(ConfigManager.getProperty("BASE_URI"))
						.and().contentType(ContentType.JSON)
						.and().accept(ContentType.ANY)
						.and().header(authHeader)
					.when().get("userdetails")
					.then().statusCode(200)
							.and().body("message", Matchers.equalTo("Success"))
							.and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailSchema.json")).log().all();
						

	}

}

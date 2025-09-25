package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsApiTest {
	
	@Test
	public void UserDetailsApiTest() {
		RestAssured.given()
						.spec(SpecUtil.requestSpecWithAuth(Role.FD))
					.when().get("userdetails")
					.then().spec(SpecUtil.responseSpec_ok())
							.and().body("message", Matchers.equalTo("Success"))
							.and().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailSchema.json")).log().all();
						

	}

}

package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.request.model.LoginUserCredentials;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginApiTest {

	@Test
	public void loginApiTest() {
		LoginUserCredentials loginUserCredentials = new LoginUserCredentials("iamfd", "password");
		RestAssured.given().spec(SpecUtil.requestSpec(loginUserCredentials)).
		when().post("login").then().spec(SpecUtil.responseSpec_ok())
				.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"))
				.and().body("message", Matchers.equalTo("Success"));

	}

}

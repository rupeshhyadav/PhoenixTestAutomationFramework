package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.pojo.LoginUserCredentials;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginApiTest {

	@Test
	public void loginApiTest() {
		LoginUserCredentials loginUserCredentials = new LoginUserCredentials("iamfd", "password");
		RestAssured.given().baseUri("http://64.227.160.186:9000/v1").and().contentType(ContentType.JSON).and()
				.accept(ContentType.JSON).and().body(loginUserCredentials).when().post("login").then().statusCode(200)
				.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"))
				.and().body("message", Matchers.equalTo("Success")).log().all();

	}

}

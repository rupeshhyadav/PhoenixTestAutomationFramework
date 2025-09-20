package com.api.tests;

import java.util.regex.Matcher;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterApiTest {

	@Test
	public void masterApiTest() {
		Header authHeader = new Header("Authorization", AuthTokenProvider.getToken(Role.FD));
		RestAssured.given().baseUri(ConfigManager.getProperty("BASE_URI")).and().header(authHeader)
				.contentType(ContentType.JSON).log().all().when().post("master").then().log().all().statusCode(200)
				.body("message", Matchers.equalTo("Success")).body("data", Matchers.notNullValue())
				.body("$", Matchers.hasKey("message")).body("$", Matchers.hasKey("data"))
				.body("data", Matchers.hasKey("mst_oem")).body("data", Matchers.hasKey("mst_model"))
				.body("data.mst_oem.size()", Matchers.equalTo(2))
				.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
				.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/masterApiResponseSchema.json"));

	}

	@Test
	public void masterApiWithoutHeaderTest() {
		RestAssured.given().baseUri(ConfigManager.getProperty("BASE_URI")).and().contentType(ContentType.JSON).log()
				.all().when().post("master").then().log().all().statusCode(401);

	}
}

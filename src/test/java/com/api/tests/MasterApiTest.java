package com.api.tests;

import java.util.regex.Matcher;

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

public class MasterApiTest {

	@Test(description = "Verifying if master API giving correct response", groups = { "api", "smoke", "regression" })
	public void masterApiTest() {

		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD)).
				
				when().post("master").then().spec(SpecUtil.responseSpec_ok())
				.body("message", Matchers.equalTo("Success")).body("data", Matchers.notNullValue())
				.body("$", Matchers.hasKey("message")).body("$", Matchers.hasKey("data"))
				.body("data", Matchers.hasKey("mst_oem")).body("data", Matchers.hasKey("mst_model"))
				.body("data.mst_oem.size()", Matchers.equalTo(2))
				.body("data.mst_oem.id", Matchers.everyItem(Matchers.notNullValue()))
				.body("data.mst_oem.name", Matchers.everyItem(Matchers.notNullValue()))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/masterApiResponseSchema.json"));

	}

	@Test(description = "Verifying if master API giving correct response status code for invalid token", groups = { "api", "negative", "regression" })
	public void masterApiWithoutHeaderTest() {
		RestAssured.given().spec(SpecUtil.requestSpec()).
				when().post("master").then().spec(SpecUtil.responseSpec_text(401));

	}
}

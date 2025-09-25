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

public class CountApiTest {

	@Test
	public void verifyCountApiResponseTest() {
	
		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD))
		
		.when()
				.get("dashboard/count").then().spec(SpecUtil.responseSpec_ok()).
				body("message", Matchers.equalTo("Success"))
				.body("data", Matchers.notNullValue())
				.body("data.size()", Matchers.equalTo(3))
				.body("data.count", Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
				.body("data.label", Matchers.not(Matchers.blankOrNullString()))
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/countApiResponseSchema.json"))
				.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));

	}
	
	@Test
	public void verifyCountApiWithoutHeaderTest() {
		RestAssured.given().spec(SpecUtil.requestSpec()).
					when().get("dashboard/count").
					then().spec(SpecUtil.responseSpec_text(401));
		
	}

}

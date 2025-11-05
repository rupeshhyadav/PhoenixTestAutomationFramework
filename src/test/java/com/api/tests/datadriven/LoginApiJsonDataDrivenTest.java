package com.api.tests.datadriven;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.request.model.LoginUserCredentials;
import com.api.utils.SpecUtil;
import com.dataprovider.api.bean.UserBean;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginApiJsonDataDrivenTest {
	
	@Test(description = "Verifying if login API is working for FD user", 
			groups = { "api", "smoke", "regression" },
			dataProviderClass = com.dataprovider.DataProviderUtils.class,
			dataProvider = "loginApiJsonDataProvider"
			)
	public void loginApiTest(LoginUserCredentials loginUserCredentials) {

		RestAssured.given().spec(SpecUtil.requestSpec(loginUserCredentials)).when().post("login").then()
				.spec(SpecUtil.responseSpec_ok()).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"))
				.and().body("message", Matchers.equalTo("Success"));

	}

}

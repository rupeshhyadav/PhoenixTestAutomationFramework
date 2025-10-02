package com.api.utils;

import org.apache.http.auth.UsernamePasswordCredentials;

import com.api.request.model.LoginUserCredentials;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AuthTokenProvider {

	public static String getToken(Role role) {
		LoginUserCredentials loginUserCredentials = null;
		if (role == role.FD) {
			loginUserCredentials = new LoginUserCredentials("iamfd", "password");

		} else if (role == role.SUP) {
			loginUserCredentials = new LoginUserCredentials("iamsup", "password");
		} else if (role == role.ENG) {
			loginUserCredentials = new LoginUserCredentials("iameng", "password");
		} else if (role == role.QC) {
			loginUserCredentials = new LoginUserCredentials("iamqc", "password");
		}

		String token = RestAssured.given().baseUri(ConfigManager.getProperty("BASE_URI")).contentType(ContentType.JSON)
				.accept(ContentType.JSON).body(loginUserCredentials).when().post("login").then().statusCode(200)
				.extract().jsonPath().getString("data.token");
		return token;

	}

}

package com.api.utils;

import org.apache.http.client.methods.RequestBuilder;
import org.hamcrest.Matchers;

import com.api.request.model.LoginUserCredentials;
import com.apj.constants.Role;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).build();
		return request;

	}

	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setAccept(ContentType.JSON).setContentType(ContentType.JSON).setBody(payload).build();
		return request;

	}

	public static RequestSpecification requestSpecWithAuth(Role role) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).build();
		return request;

	}

	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload).build();
		return request;

	}

	public static ResponseSpecification responseSpec_ok() {
		ResponseSpecification response = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(1000L)).log(LogDetail.ALL).build();
		return response;
	}

	public static ResponseSpecification responseSpec_text(int statusCode) {
		ResponseSpecification response = new ResponseSpecBuilder().expectStatusCode(statusCode).build();
		return response;
	}

	public static ResponseSpecification responseSpec_json(int statusCode) {
		ResponseSpecification response = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectContentType(ContentType.JSON).build();
		return response;
	}

}

package com.api.tests.datadriven;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtil;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobApiDataDrivenFakerTest {

	@Test(description = "Verifying if create job API giving correct response and able to create Inwarranty job", groups = {
			"api", "smoke",
			"regression" },
			dataProviderClass = com.dataprovider.DataProviderUtils.class,
			dataProvider = "createJobFakeDataProvider")
	public void createJobApiTest(CreateJobPayload createJobPayload) {
		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("job/create")
				.then().spec(SpecUtil.responseSpec_ok())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_platform_id", Matchers.equalTo(2)).body("data.job_number", Matchers.startsWith("JOB_"));
	}

}

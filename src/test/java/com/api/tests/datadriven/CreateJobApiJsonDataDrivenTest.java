package com.api.tests.datadriven;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtil;
import com.apj.constants.Role;

import io.restassured.RestAssured;

public class CreateJobApiJsonDataDrivenTest {

	@Test(description = "Verifying if create job API giving correct response", groups = { "api", "smoke",
			"regression" }, dataProviderClass = com.dataprovider.DataProviderUtils.class, dataProvider = "createJobApiJsonDataProvider")
	public void CreateJobApiDataDrivenTest(CreateJobPayload createJobPayload) {
		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("job/create")
				.then().spec(SpecUtil.responseSpec_ok()).body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_platform_id", Matchers.equalTo(2)).body("data.job_number", Matchers.startsWith("JOB_"));

	}

}

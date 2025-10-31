package com.api.tests.datadriven;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.DateTimeUtil;
import com.api.utils.SpecUtil;
import com.apj.constants.MST_MODEL;
import com.apj.constants.OEM;
import com.apj.constants.Platform;
import com.apj.constants.Problem;
import com.apj.constants.Product;
import com.apj.constants.Role;
import com.apj.constants.ServiceLocation;
import com.apj.constants.Warranty_Status;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobApiDataDrivenTest {

	@Test(description = "Verifying if create job API giving correct response and able to create Inwarranty job", groups = {
			"api", "smoke",
			"regression" }, dataProviderClass = com.dataprovider.DataProviderUtils.class, dataProvider = "createJobApiDataProvider")
	public void createJobApiTest(CreateJobPayload createJobPayload) {

		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("job/create")
				.then().spec(SpecUtil.responseSpec_ok())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_platform_id", Matchers.equalTo(2)).body("data.job_number", Matchers.startsWith("JOB_"));

	}

}

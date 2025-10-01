package com.api.tests;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobApiTest {

	@Test
	public void createJobApiTest() {
		Customer customer = new Customer("Rupesh", "Yadav", "9654074924", "2654074924", "rupesh@test.com",
				"rupesh1@tes.com");
		CustomerAddress customerAddress = new CustomerAddress("A", "Aban", "New Street", "Frescho", "MG Road", "560068",
				"India", "Karnatak");
		CustomerProduct customerProduct = new CustomerProduct("2025-05-29T18:30:00.000Z", "247523943969769",
				"247523943969769", "247523943969769", "2025-05-29T18:30:00.000Z", 3, 3);
		Problems problems = new Problems(4, "test123");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 2, customer, customerAddress, customerProduct,
				problemList);

		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("job/create")
				.then().spec(SpecUtil.responseSpec_ok())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_platform_id", Matchers.equalTo(2)).body("data.job_number", Matchers.startsWith("JOB_"));

	}

}

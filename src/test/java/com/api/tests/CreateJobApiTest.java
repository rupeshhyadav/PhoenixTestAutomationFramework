package com.api.tests;

import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;
import com.apj.constants.Role;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateJobApiTest {
	

	@Test
	public void createJobApiTest() {
		Customer customer = new Customer("Rupesh", "Yadav", "9654074924", "2654074924", "rupesh@test.com", "rupesh1@tes.com");
		CustomerAddress customerAddress = new CustomerAddress("A", "Aban", "New Street", "Frescho", "MG Road", "560068", "India", "Karnatak");
		CustomerProduct customerProduct = new CustomerProduct("2025-05-29T18:30:00.000Z", "109513338969769", "109513338969769", "109513338969769", "2025-05-29T18:30:00.000Z", 3, 3);
		Problems problems = new Problems(4, "test123");
		Problems[] problemsArray= new Problems[1];
		problemsArray[0]=problems;
		CreateJobPayload createJobPayload = new CreateJobPayload(0,2,1,2,customer,customerAddress,customerProduct,problemsArray);
		
		RestAssured.given().spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.when().post("job/create")
		.then().spec(SpecUtil.responseSpec_ok());

	}

}

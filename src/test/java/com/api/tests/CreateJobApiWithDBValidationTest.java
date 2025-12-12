package com.api.tests;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.response.model.CreateJobResponseModel;
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
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class CreateJobApiWithDBValidationTest {
	CreateJobPayload createJobPayload;
	Customer customer;
	CustomerAddress customerAddress;
	CustomerProduct customerProduct;

	@BeforeTest(description = "Creating create job payload")
	public void setUp() {
		customer = new Customer("Rupesh", "Yadav", "9654074924", "2654074924", "rupesh@test.com", "rupesh1@tes.com");
		customerAddress = new CustomerAddress("A", "Aban", "New Street", "Frescho", "MG Road", "560068", "India",
				"Karnatak");
		customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "2475772946987234",
				"2475772946987234", "2475772946987234", DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(),
				MST_MODEL.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "test123");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);

	}

	@Test(description = "Verifying if create job API giving correct response and able to create Inwarranty job", groups = {
			"api", "smoke", "regression" })
	public void createJobApiTest() {

		CreateJobResponseModel response = RestAssured.given()
				.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload)).when().post("job/create").then()
				.spec(SpecUtil.responseSpec_ok())
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_platform_id", Matchers.equalTo(2)).body("data.job_number", Matchers.startsWith("JOB_"))
				.extract().as(CreateJobResponseModel.class);
		int customerId = response.getData().getTr_customer_id();
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		Assert.assertEquals(customer.first_name(), customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), customerDataFromDB.getLast_name());
		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDao
				.getCustomerAddressData(customerDataFromDB.getTr_customer_address_id());
		Assert.assertEquals(customerAddress.flat_number(), customerAddressDBModel.getFlat_number());
		int customerProductId = response.getData().getTr_customer_product_id();
		CustomerProductDBModel customerProductDBData = CustomerProductDao.getCustomerProductData(customerProductId);
		Assert.assertEquals(customerProductDBData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBData.getDop(), customerProduct.dop());
		JobHeadModel jobHeadDataFromDB=JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(),createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(),createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(),createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(),createJobPayload.mst_platform_id());


	}

}

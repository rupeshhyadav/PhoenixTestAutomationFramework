package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		Customer customer = new Customer("Jatin", "Yadav1", "9654074924", "9654074924", "ee@w.com", "ee.com");
		Assert.assertEquals(customerDBData.getFirst_name(), customer.first_name());

	}

}

package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DataBaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {

	private static final String CUSTOMER_DETAIL_QUERY = """
			Select * from tr_customer where id=111646;

			""";

	public static CustomerDBModel getCustomerInfo() throws SQLException {
		Connection conn = DataBaseManager.getConnection();
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(CUSTOMER_DETAIL_QUERY);
		CustomerDBModel customerDBModel = null;
		while (result.next()) {
			customerDBModel = new CustomerDBModel(result.getString("first_name"), result.getString("last_name"),
					result.getString("mobile_number"), result.getString("mobile_number_alt"),
					result.getString("email_id"), result.getString("email_id_alt"));
		}
		return customerDBModel;

	}
}

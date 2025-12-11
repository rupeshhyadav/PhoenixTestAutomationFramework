package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DataBaseManager;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;

public class CustomerAddressDao {
	private static final String CUSTOMER_ADDRESS_DETAIL_QUERY = """
			Select id,flat_number,apartment_name,street_name,landmark,area,pincode,country,state from tr_customer_address where id= ?

			""";

	public static CustomerAddressDBModel getCustomerAddressData(int customerAddressId) {
		CustomerAddressDBModel customerAddressDBModel = null;
		try {
			Connection conn = DataBaseManager.getConnection();
			PreparedStatement pstatement = conn.prepareStatement(CUSTOMER_ADDRESS_DETAIL_QUERY);
			pstatement.setInt(1, customerAddressId);
			ResultSet result = pstatement.executeQuery();
			while (result.next()) {
				customerAddressDBModel = new CustomerAddressDBModel(result.getInt("id"),
						result.getString("flat_number"), result.getString("apartment_name"),
						result.getString("street_name"), result.getString("landmark"), result.getString("area"),
						result.getString("pincode"), result.getString("country"), result.getString("state"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return customerAddressDBModel;
	}
}

package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DataBaseManager;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao {
	private static final String CUSTOMER_ADDRESS_DETAIL_QUERY = """
			Select * from tr_customer_product where id= ?

			""";

	public static CustomerProductDBModel getCustomerProductData(int productId) {
		CustomerProductDBModel customerProductDBModel = null;
		try {
			Connection conn = DataBaseManager.getConnection();
			PreparedStatement pstatement = conn.prepareStatement(CUSTOMER_ADDRESS_DETAIL_QUERY);
			pstatement.setInt(1, productId);
			ResultSet result = pstatement.executeQuery();
			while (result.next()) {
				customerProductDBModel = new CustomerProductDBModel(result.getInt("id"),
						result.getInt("tr_customer_id"), result.getInt("mst_model_id"), result.getString("dop"),
						result.getString("popurl"), result.getString("imei2"), result.getString("imei1"),
						result.getString("serial_number"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return customerProductDBModel;
	}
}

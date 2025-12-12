package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataBaseManager {

	private static final String DB_URL = EnvUtil.getValue("DB_URL");
	private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
	private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE_COUNT"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private static Connection conn;

	private DataBaseManager() {

	}

	public static void initializePool() {
		if (hikariDataSource == null) {
			synchronized (DataBaseManager.class) {
				if (hikariDataSource == null) {
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE_COUNT);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000);
					hikariDataSource = new HikariDataSource(hikariConfig);
				}

			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		if (hikariDataSource == null) {
			initializePool();
		} else if (hikariDataSource.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		try {
			connection = hikariDataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;

	}
}

package com.api.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties prop = new Properties();
	private static String filePath = "config/config.properties";
	private static String env;

	private ConfigManager() {

	}

	static {
		env = System.getProperty("env", "qa");
		env.toLowerCase().trim();
		switch (env) {
		case "dev": {
			filePath = "config/config.dev.properties";
			break;
		}
		case "qa": {
			filePath = "config/config.qa.properties";
			break;
		}
		default:
			filePath = "config/config.uat.properties";
		}

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		if (input == null) {
			throw new RuntimeException("File not found at this path : " + filePath);
		}

		try {
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty(key);

	}

}

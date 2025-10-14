package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.dataprovider.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {

	private CSVReaderUtil() {

	}

	public static void loadCSV(String pathOfCsvFile) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCsvFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		CsvToBean<UserBean> csvToBean = new CsvToBeanBuilder(csvReader).withType(UserBean.class).withIgnoreEmptyLine(true).build();
		List<UserBean> userList= csvToBean.parse();
		

	}

}

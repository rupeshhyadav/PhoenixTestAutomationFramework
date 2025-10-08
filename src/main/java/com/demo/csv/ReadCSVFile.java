package com.demo.csv;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile {

	public static void main(String[] args) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/loginCreds.csv");
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);
		CsvToBean<UserPojo> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(UserPojo.class).withIgnoreEmptyLine(true).build();
		List<UserPojo> userList=csvToBean.parse();
		System.out.println(userList.get(0).getUsername());
	}

}

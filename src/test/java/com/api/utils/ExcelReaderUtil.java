package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.LoginUserCredentials;

public class ExcelReaderUtil {
	private ExcelReaderUtil() {

	}

	public static Iterator<LoginUserCredentials> loadExcelTestData(String filePath) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		XSSFWorkbook myworkBook = null;
		try {
			myworkBook = new XSSFWorkbook(is);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		XSSFSheet mysheet = myworkBook.getSheet("Sheet1");
		XSSFRow headerRow = mysheet.getRow(0);
		int userNameIndex = -1;
		int passwordIndex = -1;
		int lastRowIndex = mysheet.getLastRowNum();
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().equalsIgnoreCase("username")) {
				userNameIndex = cell.getColumnIndex();
			}
			if (cell.getStringCellValue().equalsIgnoreCase("password")) {
				passwordIndex = cell.getColumnIndex();
			}
		}
		XSSFRow myrow;
		List<LoginUserCredentials> userList = new ArrayList<LoginUserCredentials>();

		for (int i = 0; i <= lastRowIndex; i++) {
			myrow = mysheet.getRow(i);
			LoginUserCredentials loginUserCredentials = new LoginUserCredentials(
					myrow.getCell(userNameIndex).toString(), myrow.getCell(passwordIndex).toString());
			userList.add(loginUserCredentials);
		}

		return userList.iterator();

	}

}

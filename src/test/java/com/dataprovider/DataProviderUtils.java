package com.dataprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Login;
import com.api.request.model.LoginUserCredentials;
import com.api.utils.CSVReaderUtil;
import com.api.utils.CreateJobBeanMapper;
import com.api.utils.ExcelReaderUtil;
import com.api.utils.ExcelReaderWithPoiji;
import com.api.utils.FakerDataGenerator;
import com.api.utils.JsonReaderUtil;
import com.dataprovider.api.bean.CreateJobBean;
import com.dataprovider.api.bean.UserBean;

public class DataProviderUtils {

	@DataProvider(name = "loginApiDataProvider", parallel = true)
	public static Iterator<UserBean> loginApiDataProvider() {
		return CSVReaderUtil.loadCSV("testData/loginCreds.csv", UserBean.class);
	}

	@DataProvider(name = "createJobApiDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiDataProvider() {
		Iterator<CreateJobBean> createJobBeanIterator = CSVReaderUtil.loadCSV("testData/createJobData.csv",
				CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (createJobBeanIterator.hasNext()) {
			tempBean = createJobBeanIterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}

		return payloadList.iterator();

	}

	@DataProvider(name = "createJobFakeDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobFakeDataProvider() {
		String fakercount = System.getProperty("fakercount", "5");
		int fakercountInt = Integer.parseInt(fakercount);
		Iterator<CreateJobPayload> createJobPayloadIterator = FakerDataGenerator
				.generateFakeCreateJobData(fakercountInt);
		return createJobPayloadIterator;

	}

	@DataProvider(name = "loginApiJsonDataProvider", parallel = true)
	public static Iterator<LoginUserCredentials> loginApiJsonDataProvider() {
		return JsonReaderUtil.loadJson("testData/LoginApiTestData.json", LoginUserCredentials[].class);
	}

	@DataProvider(name = "createJobApiJsonDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiJsonDataProvider() {
		return JsonReaderUtil.loadJson("testData/CreateJobJsonData.json", CreateJobPayload[].class);

	}

	@DataProvider(name = "loginAPiExcelDataProvider", parallel = true)
	public static Iterator<LoginUserCredentials> loginAPiExcelDataProvider() {
		return ExcelReaderUtil.loadExcelTestData("testData/LoginApiTestData.xlsx");

	}

	@DataProvider(name = "loginAPiExcelPoijiDataProvider", parallel = true)
	public static Iterator<LoginUserCredentials> loginAPiExcelPoijiDataProvider() {
		return ExcelReaderWithPoiji.loadExcelTestDataFromPoiji("testData/CreateJobData.xlsx",
				LoginUserCredentials.class);

	}

	@DataProvider(name = "createJobExcelPoijiDataProvider", parallel = true)
	public static Iterator<CreateJobBean> createJobExcelPoijiDataProvider() {
		return ExcelReaderWithPoiji.loadExcelTestDataFromPoiji("testData/createJobData.xlsx", CreateJobBean.class);

	}

	@DataProvider(name = "createJobApiExcelDataProvider", parallel = true)
	public static Iterator<CreateJobPayload> createJobApiExcelDataProvider() {
		Iterator<CreateJobBean> iterator = ExcelReaderWithPoiji
				.loadExcelTestDataFromPoiji("testData/createJobData1.xlsx", CreateJobBean.class);
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		CreateJobBean tempBean;
		CreateJobPayload tempPayload;
		while (iterator.hasNext()) {
			tempBean = iterator.next();
			tempPayload = CreateJobBeanMapper.mapper(tempBean);
			payloadList.add(tempPayload);
		}
		
		return payloadList.iterator();

	}

}

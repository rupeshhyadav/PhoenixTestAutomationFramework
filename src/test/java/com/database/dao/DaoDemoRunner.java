package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.request.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataprovider.api.bean.CreateJobBean;

public class DaoDemoRunner {

	public static void main(String[] args) {
		List<CreateJobBean> beanList = CreateJobPayLoadDataDao.getCreateJobPayLoadData();
		List<CreateJobPayload> payloadList = new ArrayList<CreateJobPayload>();
		for (CreateJobBean createJobBean : beanList) {
			CreateJobPayload createJobPayload = CreateJobBeanMapper.mapper(createJobBean);
			payloadList.add(createJobPayload);
		}

		System.out.println("-----------------------------------");
		for (CreateJobPayload payload : payloadList) {
			System.out.println(payload);
		}

	}

}

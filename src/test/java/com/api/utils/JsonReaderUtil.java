package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.LoginUserCredentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import groovyjarjarantlr4.v4.runtime.misc.ObjectEqualityComparator;

public class JsonReaderUtil {

	public static <T> Iterator<T> loadJson(String FileName, Class<T[]> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(FileName);
		ObjectMapper om = new ObjectMapper();
		T[] classArray;
		List<T> list = null;
		try {
			classArray = om.readValue(is, clazz);
			list = Arrays.asList(classArray);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.iterator();
	}

}

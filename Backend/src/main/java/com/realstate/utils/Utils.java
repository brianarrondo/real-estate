package com.realstate.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utils {
	static ObjectMapper mapper = new ObjectMapper().configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
	
	public static boolean sameMonthAndYear(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
	}
	
	public static <T> String objToJson(T obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}
	
	public static String getExceptionResponseMsg(Exception e) {
		return getResponseMsg(e.getMessage());
	}
	
	public static String getResponseMsg(String msg) {
		return "{'msg': '"+ msg +"'}";
	}
}

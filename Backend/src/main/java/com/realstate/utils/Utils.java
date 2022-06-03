package com.realstate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class Utils {
	static ObjectMapper mapper = new ObjectMapper().configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
	
	@Autowired
    static ModelMapper modelMapper;
	
	public static boolean sameMonthAndYear(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
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
		return "{\"msg\": \""+ msg +"\"}";
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	public static <T> T map(Object source, Class<T> destinationType) {
		return new ModelMapper().map(source, destinationType);
	}
	
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}
	
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getHours(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinutes(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	public static int getSeconds(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	public static Date getDate(int day, int month, int year, int hours, int minutes, int seconds) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR_OF_DAY, hours);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.SECOND, seconds);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static Date getDateFromString(String dateStr) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
		} catch (ParseException e) {
			return null;
		}  
	}
}

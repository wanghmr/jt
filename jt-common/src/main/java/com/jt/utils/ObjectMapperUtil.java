package com.jt.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 作用: 实现对象与JSON串之间的转化
 * @author Administrator
 *
 */
public class ObjectMapperUtil {
	
	//常量对象 可以调用对象的方法 线程安全 不安全???
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
	 * 将检查异常转化为运行时异常.
	 * @param data
	 * @return
	 */
	public static String toJSON(Object data) {
		String json = null;
		try {
			json = MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return json;
	}
	
	
	/**
	 * 根据JSON转化为对象 
	 * 参数:json数据,Class
	 * 返回值:由用户决定.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json,Class<T> target) {
		T obj = null;
		try {
			obj = MAPPER.readValue(json, target);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return obj;
	}
}

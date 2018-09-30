package com.utils.database.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultSetUtils {

	public static <T> List<T> parseRresultSet(ResultSet rs, Class<T> clazz) {
		List<T> resultSet = new ArrayList<>();
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int column = rsMetaData.getColumnCount();

			while (rs.next()) {
				T obj = newInstance(clazz);
				for (int i = 1; i <= column; i++) {
					String attr = rsMetaData.getColumnName(i);
					Object val = rs.getObject(i);
					invokeSet(obj, attr, val);
				}
				resultSet.add(obj);
			}
		} catch (Exception e) {
			log.error("解析ResultSet失败", e);
		}

		return resultSet;
	}

	private static <T> T newInstance(Class<T> clazz) throws Exception {
		Constructor<T> constructor = clazz.getDeclaredConstructor();
		return constructor.newInstance();
	}

	private static void invokeSet(Object obj, String attrName, Object attrVal) {
		Class<?> clazz = obj.getClass();
		Method method;
		try {
			method = clazz.getDeclaredMethod("set" + attrName, attrVal.getClass());
			method.invoke(obj, attrVal);
		} catch (Exception e) {
			log.error("设置Class: [{}], 属性:[{}] 失败", clazz, attrName);
			log.error("设置属性值异常", e);
		}
	}

}

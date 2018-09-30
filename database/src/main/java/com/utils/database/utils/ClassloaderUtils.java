package com.utils.database.utils;

import static org.mybatis.generator.internal.util.ClassloaderUtility.getCustomClassloader;

import java.util.Map;

import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.utils.DataBaseType;

public class ClassloaderUtils {

	public static void addClassLoader(DataBaseType dataBaseType, String jarUrl) {
		Map<DataBaseType, ClassLoader> classLoaders = getCustomClassloader(Map.of(dataBaseType, jarUrl));
		ObjectFactory.addExternalClassLoader(classLoaders);
	}

	public static ClassLoader getClassLoader(DataBaseType dataBaseType) {
		return ObjectFactory.getExternalClassLoader(dataBaseType);
	}
}

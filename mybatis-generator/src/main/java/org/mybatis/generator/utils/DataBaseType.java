package org.mybatis.generator.utils;

import lombok.Getter;

/**
 * 数据库类型
 * 
 * @author: 张礼佳
 * @date: 2018年9月27日 下午3:49:51
 */
@Getter
public enum DataBaseType {

	MYSQL("mysql", "mysql-connector-java-5.1.7.jar", "com.mysql.jdbc.Driver", "jdbc:mysql://", "show databases;"), //
	Oracle_11g("", "", "", "", ""), //
	DEFAULT;

	private static String DRIVER_PATH = "/driver/";

	private DataBaseType() {
	}

	private DataBaseType(String name, String jarUrl, String driverClass, String connectionProtocol,
			String queryDataBase) {
		this.name = name;
		this.jarUrl = jarUrl;
		this.driverClass = driverClass;
		this.connectionProtocol = connectionProtocol;
		this.queryDataBase = queryDataBase;
	}

	private String name; // 类型
	private String jarUrl;// 驱动jar位置
	private String driverClass; // 驱动类
	private String connectionProtocol;// 连接协议

	// ------------------------------------------------
	private String queryDataBase;// 查询数据

	public String getJarUrl() {
		return DRIVER_PATH + this.jarUrl;
	}

	/** 获取数据类型 */
	public static DataBaseType getDataBaseType(String name) {
		if (name != null)
			for (DataBaseType baseType : DataBaseType.values())
				if (name.toLowerCase().equals(baseType.getName()))
					return baseType;
		throw new RuntimeException("不支持的数据库");
	}

}

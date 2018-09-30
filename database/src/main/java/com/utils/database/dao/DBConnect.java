package com.utils.database.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mybatis.generator.internal.db.ConnectionFactory;
import org.mybatis.generator.utils.DataBaseType;
import org.springframework.stereotype.Service;

import com.utils.database.entity.DBConnectInfo;
import com.utils.database.utils.ClassloaderUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DBConnect {

	private Map<DataBaseType, Driver> drivers = new HashMap<>();

	public void closeConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				if (log.isDebugEnabled())
					throw new RuntimeException(e);
			}
	}

	/** 获取连接 */
	public Connection getConnection(DBConnectInfo dataBaseInfo) {
		Driver driver = getDriver(dataBaseInfo);
		if (driver == null)
			throw new RuntimeException("driver load fail");
		Properties info = new Properties();
		info.setProperty("user", dataBaseInfo.getUserName());
		info.setProperty("password", dataBaseInfo.getPassWord());

		String url = dataBaseInfo.getUrl();

		try {
			Connection connection = driver.connect(url, info);
			return connection;
		} catch (SQLException e) {
			log.error("获取数据库连接失败", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	public Driver getDriver(DBConnectInfo dataBaseInfo) {
		if (drivers.containsKey(dataBaseInfo.getBaseType()))
			return drivers.get(dataBaseInfo.getBaseType());
		return getDriver(dataBaseInfo.getBaseType(), dataBaseInfo.getJarUrl(), dataBaseInfo.getDriverClass());
	}

	private Driver getDriver(DataBaseType dataBaseType, String jarUrl, String driverClass) {
		if (getClassLoader(dataBaseType) == null)
			setClassLoader(dataBaseType, jarUrl);
		Driver driver = ConnectionFactory.getInstance().getDriver(dataBaseType, driverClass);
		drivers.put(dataBaseType, driver);
		return driver;
	}

	private void setClassLoader(DataBaseType dataBaseType, String jarUrl) {
		ClassloaderUtils.addClassLoader(dataBaseType, jarUrl);
	}

	private ClassLoader getClassLoader(DataBaseType dataBaseType) {
		return ClassloaderUtils.getClassLoader(dataBaseType);
	}

}

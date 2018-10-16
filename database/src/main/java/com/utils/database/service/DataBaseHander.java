package com.utils.database.service;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utils.database.dao.DBConnect;
import com.utils.database.entity.DBConnectInfo;

import lombok.extern.slf4j.Slf4j;

/***
 * 
 * 
 * @Description: 数据库处理
 * @author: 张礼佳
 * @date: 2018年9月29日 下午2:22:34
 */
@Component
@Slf4j
public class DataBaseHander {

	@Autowired
	private DBConnect connect;

	/** 测试连接 */
	public Object testConnection(DBConnectInfo dbConnectInfo) {
		try (Connection conn = getConnection(dbConnectInfo)) {
			if (conn != null)
				return Boolean.TRUE;
			return Boolean.FALSE;
		} catch (Exception e) {
			log.debug("", e);
			return e.getMessage();
		}

	}

	/** 获取数据库连接 */
	public Connection getConnection(DBConnectInfo dataBaseInfo) {
		return connect.getConnection(dataBaseInfo);
	}

}

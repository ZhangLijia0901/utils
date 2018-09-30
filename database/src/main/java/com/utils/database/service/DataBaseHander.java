package com.utils.database.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utils.database.dao.DBConnect;
import com.utils.database.entity.DBConnectInfo;
import com.utils.database.entity.DataBase;
import com.utils.database.sql.ExecuteSql;
import com.utils.database.utils.ResultSetUtils;

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

	@Autowired
	private ExecuteSql executeSql;

	public List<DataBase> getDataBases(DBConnectInfo dbConnectInfo) {
		try (Connection conn = getConnection(dbConnectInfo)) {
			ResultSet rs = executeSql.querySql(conn, dbConnectInfo.getBaseType().getQueryDataBase());
			List<DataBase> dataBases = ResultSetUtils.parseRresultSet(rs, DataBase.class);
			return dataBases;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

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

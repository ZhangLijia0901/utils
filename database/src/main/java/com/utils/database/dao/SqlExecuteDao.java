package com.utils.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utils.database.entity.DBConnectInfo;
import com.utils.database.utils.ResultSetUtils;

@Component
public class SqlExecuteDao {

	@Autowired
	private DBConnect connect;
	@Autowired
	private ExecuteSql executeSql;

	public Integer execute(DBConnectInfo dbConnectInfo, String sql, Object... params) {
		try (Connection conn = connect.getConnection(dbConnectInfo)) {
			return executeSql.ohterSql(conn, sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> execute(DBConnectInfo dbConnectInfo, String sql, Class<T> clazz, Object... params) {
		try (Connection conn = connect.getConnection(dbConnectInfo)) {
			ResultSet resultSet = executeSql.querySql(conn, dbConnectInfo.getBaseType().getDataBase().showDataBase());
			return ResultSetUtils.parseRresultSet(resultSet, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}

package com.utils.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExecuteSql {

	private void printLog(String sql, Object... objs) {
		if (log.isDebugEnabled()) {
			log.debug("exceute sql : {}", sql);
			StringBuilder builder = new StringBuilder();
			for (Object object : objs) {
				builder.append(object);
				if (object != objs[objs.length - 1])
					builder.append(", ");

			}
			log.debug("  paramter : {}", builder.substring(0, builder.length()));
		}

	}

	public int ohterSql(Connection conn, String sql, Object... objs) {
		if (conn == null)
			throw new NullPointerException("Connection is null");
		if (sql == null)
			throw new NullPointerException("sql is null");
		printLog(sql, objs);
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++)
				statement.setObject(i, objs[i]);
			return statement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 查询 sql
	 * 
	 * @return
	 */
	public ResultSet querySql(Connection conn, String sql, Object... objs) {
		if (conn == null)
			throw new NullPointerException("Connection is null");
		if (sql == null)
			throw new NullPointerException("sql is null");
		printLog(sql, objs);
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			for (int i = 0; i < objs.length; i++)
				statement.setObject(i, objs[i]);

			return statement.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

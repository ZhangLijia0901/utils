package com.utils.database.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utils.database.dao.DBConnect;
import com.utils.database.dao.ExecuteSql;
import com.utils.database.entity.DBConnectInfo;
import com.utils.database.entity.DataBase;
import com.utils.database.utils.ResultSetUtils;

@Service
public class DataBaseService {

	@Autowired
	private DBConnect connect;

	@Autowired
	private ExecuteSql executeSql;

	/** 获取数据库 */
	public List<DataBase> getDataBaseInfos(DBConnectInfo dbConnectInfo) {
		try (Connection conn = connect.getConnection(dbConnectInfo)) {
			ResultSet rs = executeSql.querySql(conn, dbConnectInfo.getBaseType().getDataBase().showDataBase());
			List<DataBase> dataBases = ResultSetUtils.parseRresultSet(rs, DataBase.class);
			return dataBases;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

//	public boolean createDataBase() {
//		
//	}

//	public boolean updateDataBase() {
//		
//	}
//	
//	public boolean deleteDataBase() {
//		
//	}
}

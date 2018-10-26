package com.utils.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.utils.database.dao.SqlExecuteDao;
import com.utils.database.entity.DBConnectInfo;
import com.utils.database.entity.DataBase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataBaseService {

	@Autowired
	private SqlExecuteDao sqlExecuteDao;

	/** 获取数据库 */
	public List<DataBase> getDataBaseInfos(DBConnectInfo dbConnectInfo) {
		return sqlExecuteDao.execute(dbConnectInfo, dbConnectInfo.getBaseType().getDataBase().showDataBase(),
				DataBase.class);
	}

	/** 创建数据库 */
	public Object createDataBase(DBConnectInfo dbConnectInfo, DataBase dataBase) {
		try {
			if (StringUtils.isEmpty(dataBase.getName()))
				return "database name is null!";
			for (DataBase db : getDataBaseInfos(dbConnectInfo))
				if (db.getName().equals(dataBase.getName()))
					return "database exists!";

			Integer result = sqlExecuteDao.execute(dbConnectInfo,
					dbConnectInfo.getBaseType().getDataBase().createDataBase(dataBase.getName()));
			if (result > 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		} catch (Exception e) {
			log.error("创建数据库失败", e);
			return e.getMessage();
		}
	}

//	public boolean updateDataBase() {
//		
//	}
//	
	public Object dropDataBase(DBConnectInfo dbConnectInfo, DataBase dataBase) {
		try {
			if (StringUtils.isEmpty(dataBase.getName()))
				return "database name is null!";
			boolean flag = Boolean.FALSE;
			for (DataBase db : getDataBaseInfos(dbConnectInfo))
				if (db.getName().equals(dataBase.getName()))
					flag = Boolean.TRUE;
			if (flag == Boolean.FALSE)
				return "database not exists!";

			Integer result = sqlExecuteDao.execute(dbConnectInfo,
					dbConnectInfo.getBaseType().getDataBase().dropDataBase(dataBase.getName()));
			if (result > 0)
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		} catch (Exception e) {
			log.error("删除数据库失败", e);
			return e.getMessage();
		}
	}
}

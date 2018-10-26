package org.mybatis.generator.utils;

import org.mybatis.generator.utils.DataBaseCustom.CommonDataBase;

public interface DataBaseCustom {
	/** 查询所有数据库 */
	String showDataBase();

	/** 创建数据库 */
	String createDataBase(Object... param);

	/** 删除数据库 */
	String dropDataBase(Object... param);

	/** 公共的 db sql */
	abstract class CommonDataBase implements DataBaseCustom {
		@Override
		public String showDataBase() {
			return "show databases;";
		}

		@Override
		public String createDataBase(Object... param) {
			return String.format("create database %s;", param[0]);
		}

		@Override
		public String dropDataBase(Object... param) {
			return String.format("drop database %s;", param[0]);
		}
	}
}

class PostgreSQL extends CommonDataBase implements DataBaseCustom {

	@Override
	public String createDataBase(Object... param) {
		return null;
	}

}

class Oracle extends CommonDataBase implements DataBaseCustom {

	@Override
	public String createDataBase(Object... param) {
		return null;
	}
}

class MySql extends CommonDataBase implements DataBaseCustom {
}

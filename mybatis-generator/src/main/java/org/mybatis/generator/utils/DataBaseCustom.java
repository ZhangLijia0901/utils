package org.mybatis.generator.utils;

import org.mybatis.generator.utils.DataBaseCustom.CommonDataBase;

public interface DataBaseCustom {
	/** 查询所有数据库 */
	String showDataBase();

	/** 创建数据库 */
	String createDataBase(Object... param);

	/** 公共的 db sql */
	abstract class CommonDataBase implements DataBaseCustom {
		@Override
		public String showDataBase() {
			return "show databases;";
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
	@Override
	public String createDataBase(Object... param) {
		return null;
	}

}

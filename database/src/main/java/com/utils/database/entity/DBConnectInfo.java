package com.utils.database.entity;

import org.mybatis.generator.utils.DataBaseType;

import com.utils.database.service.FilePathService;

import lombok.Data;

@Data
public class DBConnectInfo {

	private String host;// 主机
	private int port;// 端口
	private String database; // 数据库
	private String url; // URL
	private String type;// 类型
	private String userName;// 用户名
	private String passWord;// 密码

	private DataBaseType baseType;

	public String getJarUrl() {
		if (FilePathService.getInstance() != null)
			return FilePathService.getInstance().getDriverJarUrl(getDataBaseType());
		else
			return getDataBaseType().getJarUrl();
	}

	public String getDriverClass() {
		return this.getBaseType().getDriverClass();
	}

	public void setType(String type) {
		if (type == null)
			throw new NullPointerException("type is null");
		this.baseType = DataBaseType.getDataBaseType(type);
		this.type = type;
	}

	public DataBaseType getDataBaseType() {
		if (this.baseType == null)
			setType(this.type);
		return this.baseType;
	}

	public void setDatabase(String database) {
		if (this.host == null)
			throw new NullPointerException("host is null");
		if (this.port <= 0)
			throw new NullPointerException("port is null");
		this.database = database;
		this.url = host + ":" + port + (this.database != null ? "/" + this.database : "");
	}

	public void setUrl(String url) {
		if (url != null)
			this.url = url;
		if (this.url != null)
			return;
		if (this.host == null)
			throw new NullPointerException("host is null");
		if (this.port <= 0)
			throw new NullPointerException("port is null");
		this.url = host + ":" + port + (this.database != null ? "/" + this.database : "");

	}

	public String getUrl() {
		if (url == null)
			setUrl(null);
		if (url.indexOf("://") != -1)
			return url;
		else
			return getDataBaseType().getConnectionProtocol() + url;
	}

}

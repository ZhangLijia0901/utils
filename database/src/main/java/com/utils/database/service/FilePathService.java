package com.utils.database.service;

import java.io.File;
import java.io.IOException;

import org.mybatis.generator.utils.DataBaseType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FilePathService implements InitializingBean {

	private static FilePathService filePathService = new FilePathService();

	public static FilePathService getInstance() {
		return filePathService;
	}

	@Value("${custom.driver.path:null}")
	private String driverPath;

	/** 获取项目路径 */
	public static String getCurrentPath() {
		File file = new File("");
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			return System.getProperty("user.dir");
		}
	}

	/** 获取jar路径 */
	public String getDriverJarUrl(DataBaseType dataBaseType) {
		if (StringUtils.isEmpty(this.driverPath) || "null".equals(this.driverPath))
			this.driverPath = getCurrentPath();
		return driverPath + dataBaseType.getJarUrl();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		filePathService = this;
	}

}

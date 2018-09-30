package com.utils.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * 
 * @Description: TODO
 * @author: 张礼佳
 * @date: 2018年9月28日 下午4:47:00
 */
@SpringBootApplication(scanBasePackages = { "com.utils.database" })
public class App {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		SpringApplication.run(App.class, args);

//		DBConnectInfo dataBaseInfo = new DBConnectInfo();
//		dataBaseInfo.setType("mysql");
//		dataBaseInfo.setUrl("127.0.0.1:3306/test");
//		dataBaseInfo.setHost("127.0.0.1");
//		dataBaseInfo.setPort(3306);
//
//		dataBaseInfo.setUserName("root");
//		dataBaseInfo.setPassWord("x5");
//		GeneratorModel generatorModel = new GeneratorModel();
//		generatorModel.setTargetProject("test");
//		generatorModel.setModelPackage("com.test.entity");
//		generatorModel.setMapperPackage("com.test.mapper");
//		generatorModel.setXmlPackage("mapper");
//
//		System.err.println(dataBaseInfo.getUrl());
//		Connection connection = new DBConnect().getConnection(dataBaseInfo);
//		System.err.println(connection);
//
//		ResultSet executeQuery = connection.prepareStatement("show databases;").executeQuery();
//		List<DataBase> dataBases = ResultSetUtils.parseRresultSet(executeQuery, DataBase.class);
//		System.err.println(dataBases);

//		connection.

//		MyBatisGeneratorService batisGeneratorService = new MyBatisGeneratorService();
//		batisGeneratorService.createConfiguration(dataBaseInfo).setGeneratorConfig(generatorModel).addTable("user_role",
//				null);

//		System.err.println(driver.acceptsURL(dataBaseInfo.getUrl()));

	}

}

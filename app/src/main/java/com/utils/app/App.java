package com.utils.app;

import java.lang.reflect.Field;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import sun.misc.Unsafe;

//import jdk.internal.misc.Unsafe;

/**
 * 
 * 
 * @Description: TODO
 * @author: 张礼佳
 * @date: 2018年9月28日 下午4:47:00
 */
@SpringBootApplication(scanBasePackages = { "com.utils.database" })
public class App {

	static synchronized Unsafe getUnsafe() {
		System.err.println(Thread.currentThread().getName());
		try {
			Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafeField.setAccessible(true);
			return (Unsafe) theUnsafeField.get(null); // unsafeInstance就是Unsafe的实例
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Long i = 128L;
		Long i1 = 128L;
		System.err.println(i == i1);
		String str1 = "str";
		String str2 = "ing";

		String str3 = "str" + "ing";// 常量池中的对象
		String str4 = str1 + str2; // 在堆上创建的新的对象
		String str5 = "string";// 常量池中的对象
		System.err.println(str4 == str5);

//		Map<Integer, Integer> map = new HashMap<>(2, 0.75f);
//		map.put(5, 5);
//
//		new Thread(() -> {
//			map.put(3, 3);
//		}).start();
//		new Thread(() -> {
//			map.put(7, 7);
//		}).start();

//		SpringApplication.run(App.class, args);

//		new DBConnect();

//		String url = "jar:file:/F:/github/utils-parent/app/target/utils.jar!/BOOT-INF/lib/database-0.0.1-SNAPSHOT.jar!/META-INF/driver/mysql-connector-java-5.1.7.jar!/";
//		url = url.substring(0, url.lastIndexOf(".jar") + 4);
//		url = url.substring(url.lastIndexOf("/") + 1);
//		System.err.println(url);
//		URL url2 = new URL(url);
//		System.err.println(url2.openConnection());
//		System.err.println(new File("F:\\github\\utils-parent\\app\\target\\utils.jar").exists());

//		ClassLoader createClassLoader = JarLoader.createClassLoader(DataBaseType.MYSQL.getJarUrl());

//		Class<?> clazz = Class.forName("com.mysql.jdbc.Driver", true, createClassLoader);
//		System.err.println(clazz);
//		System.err.println("\n\n\n");
//		ClassloaderUtils.addClassLoader(DataBaseType.MYSQL,
//				"F:/github/utils-parent/app/target/utils.jar!/META-INF/driver/mysql-connector-java-5.1.7.jar");
//		SpringApplication.run(App.class, args);

//		DBConnectInfo dataBaseInfo = new DBConnectInfo();
//		dataBaseInfo.setType("PostgreSQL");
//		dataBaseInfo.setUrl("db.vr.weilian.cn:5432/vr-cmp");
//		dataBaseInfo.setHost("db.vr.weilian.cn");
//		dataBaseInfo.setPort(3306);

//		dataBaseInfo.setUserName("postgres");
//		dataBaseInfo.setPassWord("suneeedba");
//		GeneratorModel generatorModel = new GeneratorModel();
//		generatorModel.setTargetProject("test");
//		generatorModel.setModelPackage("com.suneee.scn.cmp.model");
//		generatorModel.setMapperPackage("com.suneee.scn.cmp.dao");
//		generatorModel.setXmlPackage("mybatisMap");
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
//		batisGeneratorService.createConfiguration(dataBaseInfo).setGeneratorConfig(generatorModel).addTable("cmp_quota_source",
//				null);
//		batisGeneratorService.generate();

//		System.err.println(driver.acceptsURL(dataBaseInfo.getUrl()));

	}

}

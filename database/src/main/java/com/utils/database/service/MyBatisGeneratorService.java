package com.utils.database.service;

import java.util.UUID;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.stereotype.Service;

import com.utils.database.entity.DBConnectInfo;
import com.utils.database.entity.GeneratorModel;
import com.utils.database.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyBatisGeneratorService {
	private ThreadLocal<Configuration> local = new ThreadLocal<>();

	/** 创建配置 */
	public MyBatisGeneratorService createConfiguration(DBConnectInfo dataBaseInfo) {
		Configuration configuration = new Configuration();
		configuration.addClasspathEntry(dataBaseInfo.getBaseType(), dataBaseInfo.getJarUrl());
		local.set(configuration);
		this.addContext().setCommConfig().setJdbcConfig(dataBaseInfo);
		return this;
	}

	/** 添加Context */
	private MyBatisGeneratorService addContext() {
		Context context = new Context(null);
		context.setId(UUID.randomUUID().toString());
		context.setTargetRuntime("MyBatis3");
		context.setDataBaseType(local.get().getDataBaseType());
		local.get().addContext(context);
		return this;
	}

	/** 设置公共配置 */
	private MyBatisGeneratorService setCommConfig() {
		CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
		commentGeneratorConfiguration.addProperty("suppressDate", "true");
		commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
		local.get().getContext().setCommentGeneratorConfiguration(commentGeneratorConfiguration);
		return this;
	}

	/** 设置jdbc 配置 */
	private MyBatisGeneratorService setJdbcConfig(DBConnectInfo dataBaseInfo) {
		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setDriverClass(dataBaseInfo.getBaseType().getDriverClass());
		jdbcConnectionConfiguration.setConnectionURL(dataBaseInfo.getUrl());
		jdbcConnectionConfiguration.setUserId(dataBaseInfo.getUserName());
		jdbcConnectionConfiguration.setPassword(dataBaseInfo.getPassWord());
		local.get().getContext().setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
		return this;
	}

	/** 设置生成配置 */
	public MyBatisGeneratorService setGeneratorConfig(GeneratorModel model) {
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetPackage(model.getModelPackage());
		javaModelGeneratorConfiguration.setTargetProject(model.getTargetProject());
		javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
		javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
		local.get().getContext().setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetPackage(model.getXmlPackage());
		sqlMapGeneratorConfiguration.setTargetProject(model.getTargetProject());
		sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
		local.get().getContext().setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
		javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
		javaClientGeneratorConfiguration.setTargetProject(model.getTargetProject());
		javaClientGeneratorConfiguration.setTargetPackage(model.getMapperPackage());
		local.get().getContext().setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
		return this;
	}

	/** 添加表 */
	public MyBatisGeneratorService addTable(String tableName, String entityName) {
		if (entityName == null)
			entityName = StringUtils.convertHump(tableName, true);
		TableConfiguration tc = new TableConfiguration(local.get().getContext());
		local.get().getContext().addTableConfiguration(tc);
		tc.setTableName(tableName);
		tc.setDomainObjectName(entityName);
		return this;
	}

	/** 生成 */
	public String generate() {
		DefaultShellCallback shellCallback = new DefaultShellCallback(true);

		MyBatisGenerator myBatisGenerator;
		try {
			myBatisGenerator = new MyBatisGenerator(local.get(), shellCallback, null);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			log.error("生成文件失败", e);
			throw new RuntimeException(e.getMessage());
		}
		String target = local.get().getContext().getJavaModelGeneratorConfiguration().getTargetProject();
		local.set(null);
		return target;

	}
}

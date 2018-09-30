package com.utils.database.entity;

import lombok.Data;

@Data
public class GeneratorModel {

	private String targetProject;
	private String modelPackage;
	private String xmlPackage;
	private String mapperPackage;

}

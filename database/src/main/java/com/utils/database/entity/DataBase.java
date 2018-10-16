package com.utils.database.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataBase {
	private String name;

	public DataBase() {
		this.name = "1";
	}

	public void setSCHEMA_NAME(String name) {
		this.name = name;
	}

}

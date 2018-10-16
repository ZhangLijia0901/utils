package com.utils.database.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.database.entity.DBConnectInfo;
import com.utils.database.entity.DataBase;
import com.utils.database.service.DataBaseService;

/**
 * 
 * 
 * @Description: 数据库
 * @author: 张礼佳
 * @date: 2018年10月8日 上午9:06:05
 */
@RestController
@RequestMapping("db/database")
public class DatabaseController {

	@Autowired
	private DataBaseService dataBaseService;

	@GetMapping
	public List<DataBase> getDataBaseInfos(DBConnectInfo dbConnectInfo) {
		return dataBaseService.getDataBaseInfos(dbConnectInfo);
	}

}

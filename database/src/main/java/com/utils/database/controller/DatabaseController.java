package com.utils.database.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.database.common.Constant;
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
	public ResponseEntity<List<DataBase>> get(DBConnectInfo dbConnectInfo) {
		return new ResponseEntity<>(dataBaseService.getDataBaseInfos(dbConnectInfo), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> post(DBConnectInfo dbConnectInfo, DataBase dataBase) {
		Object result = dataBaseService.createDataBase(dbConnectInfo, dataBase);
		return new ResponseEntity<>(Map.of(Constant.STATUS, result), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<Map<String, Object>> delete(DBConnectInfo dbConnectInfo, DataBase dataBase) {
		Object result = dataBaseService.dropDataBase(dbConnectInfo, dataBase);
		return new ResponseEntity<>(Map.of(Constant.STATUS, result), HttpStatus.OK);
	}

}

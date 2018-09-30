package com.utils.database.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.utils.DataBaseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utils.database.entity.DBConnectInfo;
import com.utils.database.service.DataBaseHander;

/**
 * 
 * 
 * @Description: 连接
 * @author: 张礼佳
 * @date: 2018年9月30日 下午2:05:44
 */
@RestController
@RequestMapping("db/connect")
public class ConnectController {

	@Autowired
	private DataBaseHander baseHander;

	@PostMapping
	public ResponseEntity<Map<String, Object>> testConnect(@RequestBody DBConnectInfo connectInfo) {
		Object conn = baseHander.testConnection(connectInfo);
		return new ResponseEntity<>(Map.of("status",conn), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<String>> getDataType() {
		List<String> support = new ArrayList<>();
		for (DataBaseType dbt : DataBaseType.values())
			if (dbt.getName() != null)
				support.add(dbt.getName());
		return new ResponseEntity<>(support, HttpStatus.OK);

	}

}

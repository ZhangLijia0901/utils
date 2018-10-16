package com.utils.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.Cookie;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.utils.database.entity.DBConnectInfo;

public class ConnectControllerTest {

	public ConnectControllerTest(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	private MockMvc mockMvc;

	String url = "/db/connect";

	public void test() {
		try {
			System.err.println(String.format("\n开始发送: %s, %s", url, "GET"));
			get();
			System.err.println(String.format("\n开始发送: %s, %s", url, "POST"));
			post();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void get() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		String response = mvcResult.getResponse().getContentAsString();

		assertTrue("正确", status == 200);
		assertFalse("错误", status != 200);

		System.err.println("返回结果：" + status);
		System.err.println(response);
	}

	public void post() throws Exception {
		DBConnectInfo dbConnectInfo = new DBConnectInfo();
		dbConnectInfo.setHost("127.0.0.1");
		dbConnectInfo.setPort(3306);
		dbConnectInfo.setType("mysql");
		dbConnectInfo.setUserName("root");
		dbConnectInfo.setPassWord("x5");

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONString(dbConnectInfo))
				.header("JSESSIONID", "1")).andReturn();

		int status = mvcResult.getResponse().getStatus();
		String response = mvcResult.getResponse().getContentAsString();

		assertTrue("正确", status == 200);
		assertFalse("错误", status != 200);

		System.err.println("返回结果：" + status);
		System.err.println(response);

		for (Cookie cookie : mvcResult.getResponse().getCookies()) {
			System.err.println(cookie.getName() + " ------ " + cookie.getValue());
		}
		System.err.println();

	}

}

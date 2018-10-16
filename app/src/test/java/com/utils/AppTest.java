package com.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.utils.app.App;
import com.utils.database.ConnectControllerTest;
import com.utils.database.DataBaseControllerTest;
import com.utils.database.common.Constant;
import com.utils.database.entity.DBConnectInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class })
@AutoConfigureMockMvc
public class AppTest {

	@Autowired
	private MockMvc mockMvc;

	public DBConnectInfo mysql() {
		DBConnectInfo dbConnectInfo = new DBConnectInfo();
		dbConnectInfo.setHost("127.0.0.1");
		dbConnectInfo.setPort(3306);
		dbConnectInfo.setType("mysql");
		dbConnectInfo.setUserName("root");
		dbConnectInfo.setPassWord("x5");
		return dbConnectInfo;
	}

	private MockHttpSession session;

	@Before
	public void setupBefore() {
		session = new MockHttpSession();
		session.setAttribute(Constant.DB_CONNECT_INFO, mysql());
	}

	@Test
	public void testConnectController() {
		new ConnectControllerTest(mockMvc).test();
	}

	@Test
	public void testDataBaseController() {
		new DataBaseControllerTest(mockMvc, session).test();
	}

}

package com.utils.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class DataBaseControllerTest {

	public DataBaseControllerTest(MockMvc mockMvc, MockHttpSession session) {
		this.mockMvc = mockMvc;
		this.session = session;

	}

	private MockMvc mockMvc;
	private MockHttpSession session;

	String url = "/db/database";

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

		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON).session(session))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		String response = mvcResult.getResponse().getContentAsString();

		assertTrue("正确", status == 200);
		assertFalse("错误", status != 200);

		System.err.println("返回结果：" + status);
		System.err.println(response);
	}

	public void post() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url).accept(MediaType.APPLICATION_JSON)
				.session(session).param("name", "'x")).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String response = mvcResult.getResponse().getContentAsString();

		assertTrue("正确", status == 200);
		assertFalse("错误", status != 200);

		System.err.println("返回结果：" + status);
		System.err.println(response);
	}

}

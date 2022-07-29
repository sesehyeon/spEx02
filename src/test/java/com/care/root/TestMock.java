package com.care.root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.care.root.member.controller.MemberController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@RunWith(SpringRunner.class)//어떤걸로 이 테스트를 구동할껀지
@ContextConfiguration(locations= {"classpath:testMember.xml",
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class TestMock {
	@Autowired MemberController mc;
	
	MockMvc mock;
	
	
 @Before
 public void setUp() {
	 System.out.println("test 실행 전------");
	 mock=MockMvcBuilders.standaloneSetup(mc).build();
 }
 @Test
 public void testIndex() throws Exception {
	 System.out.println("-----testindex 실행");
	mock.perform(get("/index"))
	 .andDo(print())
	.andExpect(status().isOk())
	.andExpect(forwardedUrl("member/index"));
 }
 
 @Test
 @Transactional(transactionManager = "txMgr")//데이터가 롤백되서 데이터 베이스에 저장이 되지 않는다
 public void testInsert() throws Exception {
	 mock.perform(post("/insert").param("id", "8989").param("name", "도우너")).
	 andDo(print())
	 //redirect는 302를 돌려준다 302면 다른 위치로 이동
	 .andExpect(status().is3xxRedirection());
 }
 @Test
 public void testMemberview() throws Exception {
	 mock.perform(get("/memberview")).andDo(print()).
	 andExpect(status().isOk())
	 .andExpect(forwardedUrl("member/memberview"))
	 .andExpect(model().attributeExists("list"));
 
 
}
}

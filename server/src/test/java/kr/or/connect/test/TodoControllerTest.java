package kr.or.connect.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import kr.or.connect.todo.TodoApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodoApplication.class)
@Transactional
public class TodoControllerTest {

	@Autowired
	WebApplicationContext wac;
	MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(this.wac).alwaysDo(print(System.out))
				.build();
	}

	@Test
	public void shouldFetch() throws Exception{
		mvc.perform(get("/api/todos/fetchlist"));		
	}
	
	@Test
	public void shouldCreate() throws Exception{
		String requestBody="{\"todo\":\"지원하기\",\"completed\":\"0\"}";
		
		mvc.perform(
				post("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").exists())
				.andExpect(jsonPath("$.data.todo").value("지원하기"))
				.andExpect(jsonPath("$.data.completed").value("0"));
		
	}
	
	@Test
	public void shouldUpdateInfo() throws Exception{
		
		mvc.perform(
				get("/api/todos/updateInfo"))
				.andExpect(jsonPath("$.data").isNumber());
		
	}
	
	@Test
	public void shouldUpdateCompleted() throws Exception{
				
		String requestBody = "{\"id\":\"355\",\"completed\":\"1\"}";
		mvc.perform(
				put("/api/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				)
				.andExpect(jsonPath("$.data.completed").isNumber());
		
	}
	
	@Test
	public void shouldDeleteClear() throws Exception{
				
		mvc.perform(
				delete("/api/todos/clear")
				.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.data.size").isNotEmpty())
				.andExpect(jsonPath("$.data.list").isArray());
		
	}
	
	
	@Test
	public void shouldDeleteTodo() throws Exception{
		mvc.perform(
				delete("/api/todos/355")
				.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(jsonPath("$.data").exists());
		
	}

}

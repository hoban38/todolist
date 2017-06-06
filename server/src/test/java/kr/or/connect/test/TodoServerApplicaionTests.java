package kr.or.connect.test;

import kr.or.connect.todo.TodoApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TodoApplication.class)
public class TodoServerApplicaionTests {

	@Test
	public void contextLoads() {
	}
}

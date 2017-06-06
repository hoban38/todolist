package kr.or.connect.test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import kr.or.connect.todo.TodoApplication;
import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=TodoApplication.class)
@Transactional
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;
	
	@Test
	public void shouldInsertAndSelect(){
		Todo newtodo = new Todo("테스트 todo");
		Integer id = dao.insert(newtodo);
		
		Todo selected = dao.selectById(id);
		System.out.println(selected);
		assertThat(selected.getTodo(),is("테스트 todo"));
		
	}
	
	@Test
	public void shouldcount_uncompleted(){
		System.out.println(dao.count_unCompleted());
	}
	
	@Test
	public void shouldcount_all(){
		System.out.println(dao.count_all());
	}
	
	@Test
	public void selectById(){
		Integer id = new Integer(2);
		Todo selected = dao.selectById(id);
		System.out.println(selected);
	}
	
	@Test
	public void shouldListAll(){
		List<Todo> list = dao.list_all();
		for(Todo d: list){
			System.out.println(d);
		}
		assertThat(list, is(notNullValue()));
	}
	
	@Test
	public void shouldListByCompleted(){
		Integer completed = new Integer(1);
		List<Todo> list = dao.list_by_completed(completed);
		for(Todo d: list){
			System.out.println("un#"+d);
		}
		assertThat(list, is(notNullValue()));
	}
	
	@Test
	public void shouldDelete(){
		Todo todo = new Todo("가자 네이버",1);
		Integer id = dao.insert(todo);
		
		int affected = dao.deletebyId(id);
		
		assertThat(affected, is(1));
		
	}
	
	@Test
	public void shouldUpdate(){
		Todo todo = new Todo("가자 네이버 업데이트",0);
		Integer id = dao.insert(todo);
		
		todo.setId(id);
		todo.setCompleted(1);
		
		int affected = dao.update(todo);
		
		assertThat(affected, is(1));
		
		Todo updated = dao.selectById(id);
		assertThat(updated.getCompleted(), is(1));
		
	}
}

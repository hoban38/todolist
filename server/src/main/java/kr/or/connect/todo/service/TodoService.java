package kr.or.connect.todo.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
	
	private TodoDao dao;
	
	public TodoService(TodoDao dao){
		this.dao = dao;
	}

	public Todo create(Todo todo) {
		Integer id = dao.insert(todo);
		todo.setId(id);
		return todo;
	}

	public Todo findById(Integer id){
		return dao.selectById(id);
	}
	
	public Collection<Todo> findAll(){
		return dao.list_all();
	}
	
	public Collection<Todo> ListByCompleted(Integer completed){
		return dao.list_by_completed(completed);
	}
	
	public int count_unCompleted(){
		return dao.count_unCompleted();
	}
	
	public boolean update(Todo todo){
		return dao.update(todo)==1;
	}
	
	public boolean delete(Integer id){
		return dao.deletebyId(id)==1;
	}
	
	public Map clearCompleted(){
		return dao.clearCompleted();
	}
}

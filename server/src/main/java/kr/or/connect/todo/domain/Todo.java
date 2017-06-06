package kr.or.connect.todo.domain;

import java.sql.Date;

public class Todo {
	
	private Integer id;
	private String todo;
	private Integer completed;
	private Date date;
	
	Todo(){
		
	}
	public Todo(String todo){
		this.todo = todo;
		this.completed = 0;
	}

	public Todo(String todo, Integer completed) {
		this.todo = todo;
		this.completed = completed;
	}
	
	Todo(Integer id, String todo, Integer completed){
		this(todo,completed);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public Integer getCompleted() {
		return completed;
	}

	public void setCompleted(Integer completed) {
		this.completed = completed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", todo=" + todo + ", completed=" + completed
				+ ", date=" + date + "]";
	}

	
}

package kr.or.connect.todo.persistence;

public class TodoSqls {
	
	//delete
	static final String DELETE_BY_ID =
			"DELETE FROM todo WHERE id= :id";
	
	static final String CLEAR_COMPLETED = 
			"DELETE FROM todo WHERE completed=:completed";
	//select	
	static final String SELECT_BY_ID = 
			"SELECT id, todo, completed, date FROM todo where id = :id";
	
	static final String SELECT_ALL =
			"SELECT id, todo, completed, date FROM todo order by date desc";
	//select completed 1,0
	static final String SELECT_BY_COMPLETED = 
			"SELECT id, todo, completed, date FROM todo where completed=:completed";
	//count
	static final String COUNT_TODO = 
			"SELECT COUNT(*) FROM todo where completed=:completed";
	
	static final String COUNT_ALL = 
			"select COUNT(*) FROM todo";
	
	//update
	static final String UPDATE = "UPDATE todo SET\n"
			+"completed = :completed\n"
			+"where id=:id";
	

}

package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import kr.or.connect.todo.domain.Todo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id")
				.usingGeneratedKeyColumns("date");
	}

	public Integer insert(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Todo selectById(Integer id) {
		RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, rowMapper);
	}
	
	public int count_unCompleted(){
		/*아직 완료하지 못한  할일*/
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("completed",0);
		return jdbc.queryForObject(TodoSqls.COUNT_TODO, params, Integer.class);
	}
	
	public int count_all(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(TodoSqls.COUNT_ALL, params, Integer.class);
	}
	
	public List<Todo> list_by_completed(Integer completed){
		RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("completed",completed);
		return jdbc.query(TodoSqls.SELECT_BY_COMPLETED, params,rowMapper);
		
	}
	
	public List<Todo> list_all(){
		RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
		Map<String,Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ALL, params,rowMapper);
	}

	public int deletebyId(Integer id){
		Map<String,?> params = Collections.singletonMap("id", id);
		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
	}
	
	/*삭제후, 확인을 위해 Map 형식으로 전달*/
	public Map clearCompleted(){
		List<Todo> list_by_completed = list_by_completed(1);
		Map<String,?> params = Collections.singletonMap("completed", 1);
		
		Map<String,Object> list = new HashMap<String, Object>();
		list.put("size",jdbc.update(TodoSqls.CLEAR_COMPLETED, params));
		list.put("list", list_by_completed);
		return list ;
		
	}
	
	public int update(Todo todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(TodoSqls.UPDATE, params);
	}
	

	
	
}

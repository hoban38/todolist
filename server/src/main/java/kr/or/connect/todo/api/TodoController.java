package kr.or.connect.todo.api;

import java.util.List;
import java.util.Map;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.dto.JSONResult;
import kr.or.connect.todo.service.TodoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	private final Logger log = LoggerFactory.getLogger(TodoController.class);

	private final TodoService service;
	
	@Autowired
	public TodoController(TodoService service){
		this.service = service;
	}
	
	@GetMapping("/fetchlist")
	JSONResult fetchList_completed(
			@RequestParam(value="m", required=true, defaultValue="0" ) Integer completed
			){
		List<Todo> list ;
		if(completed ==-1){
			list = (List<Todo>) service.findAll();
		}else{
			list = (List<Todo>) service.ListByCompleted(completed);
		}
		return JSONResult.success(list);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	JSONResult create(@RequestBody Todo todo){
		Todo newTodo = service.create(todo);
		log.info("todo created : {}" , newTodo);
		return JSONResult.success(todo);
	}
	
	@GetMapping("/updateInfo")
	JSONResult updateInfo(){
		int count = service.count_unCompleted();
		return JSONResult.success(count);
	}
	
	@PutMapping
	JSONResult updateCompleted(@RequestBody Todo todo){
		if(service.update(todo)){
			return JSONResult.success(todo);
		}else{
			return JSONResult.error("fail to update");
		}
	}
	
	@DeleteMapping("/clear")
	JSONResult clearCompleted(){
		Map data = service.clearCompleted();
		return JSONResult.success(data);
	}
	
	@DeleteMapping("/{id}")
	JSONResult deleteTodo(@PathVariable Integer id){
		service.delete(id);
		return JSONResult.success(id);
	}
	
	
}

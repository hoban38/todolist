var render = function(vo, mode) {
	if (vo.completed == 1) {
		htmls = "<li class='completed'> " + "<div class='view'>"
				+ "<input class='toggle' type='checkbox' ";
	} else {
		htmls = "<li>" + "<div class='view'>"
				+ "<input class='toggle' type='checkbox' checked ";
	}

	htmls += "data-completed=" + vo.completed + " data-id=" + vo.id + ">"
			+ "<label>" + vo.todo + "</label>"
			+ "<button class='destroy' data-id="+vo.id+"></button>" + "</div>"
			+ "<input class='edit' value='Rule the web'>" + "</li>";
	if (mode == true) {
		$(".todo-list").prepend(htmls);
	} else {
		$(".todo-list").append(htmls);
	}
}

var fetchList = function(mode) {
	
	var senddata = "";
	if (!mode) {
		senddata = "m=-1";
	} else if ("active" == mode) {
		console.log("active")
		senddata = "m=0";
	} else if ("completed" == mode) {
		console.log("completed")
		senddata = "m=1";
	}

	$.ajax({
		url : "/api/todos/fetchlist",
		type : "get",
		dataType : "json",
		data : senddata,
		success : function(response) {
			if (response.result != "success") {
				return;
			}
			$(".todo-list li").remove();
			$(response.data).each(function(index, vo) {
				render(vo, false);
			})
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
};

var updateCompleted = function(id, completed) {
	completed = (completed==0)? 1: 0;

	$.ajax({
		url : "/api/todos",
		type : "put",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify({
			"id" : id,
			"completed" : completed
		}),
		success : function(response) {
			if (response.result != "success") {
				return;
			}
			updateInfo();
			console.log(response.data);
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
};

var deletetodo = function(id){

	$.ajax({
		url : "/api/todos/"+id,
		type : "delete",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(response) {
			if (response.result != "success") {
				return;
			}
			updateInfo();
			$("[data-id="+response.data+"]").closest("li").remove();
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
	
};

var clearCompleted = function() {
	
	$.ajax({
		url : "/api/todos/clear",
		type : "delete",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		success : function(response) {
			if (response.result != "success") {
				return;
			}
			updateInfo();
			console.log(response.data);
			$(response.data.list).each(function(index,vo){
				$("[data-id="+vo.id+"]").closest("li").remove();
			});
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
};

var updateInfo = function() {
	
	$.ajax({
		url : "/api/todos/updateInfo",
		type : "get",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		data : "completed:" + 0,
		success : function(response) {
			if (response.result != "success") {
				return;
			}
			$(".todo-count strong").text(response.data);
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
}

var addTodo = function(todotext){
	
	$.ajax({
		url : "/api/todos",
		type : "post",
		dataType : "json",
		contentType : "application/json;charset=UTF-8",
		data : JSON.stringify({
			"todo" : todotext,
			"completed" : 0
		}),
		success : function(response) {
			console.log("success");
			if (response.result != "success") {
				console.log(reponse.message);
				return;
			}
			$(response.data).each(function(index, vo) {
				render(vo, true);
			});

			$("#add-form")[0].reset();
			updateInfo();
		},
		error : function(request, status, e) {
			console.log("error" + e);
		}
	});
	
};

var init = function() {
	window.history.pushState('', '/', window.location.pathname);
	fetchList();
	updateInfo();
};

(function(window) {
	'use strict';
	init();

	$(window).hashchange(function() {
		var mode = location.hash.split('/');
		fetchList(mode[1]);
		window.history.pushState('', '', window.location.pathname)
	});

	$("#add-form").submit(function(event) {
		event.preventDefault();
		
		var todotext = $(".new-todo").val();
		if (todotext == "") {
			alert("메세지를 입력하세요.");
			return;
		}

		addTodo(todotext);
	});

	$(document).on("click", ".toggle", function() {
		var id = $(this).data('id');
		var completed = $(this).data('completed');

		if ($(this).prop('checked')) {
			$(this).closest("li").removeClass("completed");
			$(this).data('completed',0);
		} else {
			$(this).closest("li").addClass("completed");
			$(this).data('completed',1);
		}
		updateCompleted(id,completed);
	});
	
	$(document).on("click",".clear-completed",function(){
		clearCompleted();
	});
	
	$(document).on("click",".destroy",function(){
		deletetodo($(this).data('id'))
	});

})(window);

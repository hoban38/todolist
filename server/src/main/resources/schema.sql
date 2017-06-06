CREATE TABLE todo (
	id INT IDENTITY NOT NULL PRIMARY KEY AUTO_INCREMENT,
	todo TEXT,
	completed INT(1) NOT NULL DEFAULT 0,
	date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


--create schema db authorization sa;
--insert into todo(todo,completed) values('지원하기',1);
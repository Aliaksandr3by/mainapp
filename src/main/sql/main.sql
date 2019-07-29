--psql -h localhost -p 5432 -U guest employeedb

CREATE USER guest WITH SUPERUSER PASSWORD 'guest';

create database employeedb owner guest;
drop database employeedb;

CREATE TABLE "employee" (
	"employee_id" BIGINT NOT NULL PRIMARY KEY,
	"department_id" BIGINT NULL DEFAULT NULL,
	"first_name" TEXT NOT NULL,
	"gender" INTEGER NOT NULL,
	"job_title" TEXT NOT NULL,
	"last_name" TEXT NOT NULL
);

DROP TABLE IF EXISTS employee;

DELETE FROM employee WHERE employee_id = 31;

SELECT * FROM employee;
SELECT * FROM employee WHERE employee.employee_id = 1;

DELETE FROM employee WHERE employee.employee_id = 3;

INSERT INTO employee (employee_id, department_id, first_name, gender, job_title, last_name) VALUES (31, 3, 'qwe', 1, 'qweqe', 'qweqe');

UPDATE employee SET first_name='dfgdgdfg', department_Id=99,job_Title='dfgdg',gender=1,last_Name='dfgdg' WHERE employee_id = 2;


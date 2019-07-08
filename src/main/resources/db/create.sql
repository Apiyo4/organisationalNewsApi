SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 departmentname VARCHAR,
 description VARCHAR,
 numberofemployees int
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 username VARCHAR,
 departmentId,
 role
);
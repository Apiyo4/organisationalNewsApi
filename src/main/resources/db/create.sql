CREATE DATABASE organisational_news_api;
\c organisational_news_api;

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 departmentname VARCHAR,
 description VARCHAR,
 numberofemployees int
);

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 username VARCHAR,
 departmentId int,
 role VARCHAR
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 content varchar,
 departmentId int,
 description VARCHAR
 );

CREATE DATABASE organisational_news_api_test WITH TEMPLATE organisational_news_api;
CREATE DATABASE organisational_news_api;
\c organisational_news_api;

CREATE TABLE IF NOT EXISTS departments (
 id serial PRIMARY KEY,
 departmentname VARCHAR,
 description VARCHAR,
 numberofemployees int
);

CREATE TABLE IF NOT EXISTS users (
 id serial PRIMARY KEY,
 username VARCHAR,
 departmentId int,
 role VARCHAR
);

CREATE TABLE IF NOT EXISTS news (
 id serial PRIMARY KEY,
 content varchar,
 departmentId int,
 description VARCHAR
 );

CREATE DATABASE organisational_news_api_test WITH TEMPLATE organisational_news_api;
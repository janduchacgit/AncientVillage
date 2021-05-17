-- CREATE DATABASE
-- CREATE DATABASE IF NOT EXISTS ancient;

-- DROP tables
DROP TABLE IF EXISTS user_activation;
DROP TABLE IF EXISTS user_storage;
DROP TABLE IF EXISTS user_production;
DROP TABLE IF EXISTS user_building;
DROP TABLE IF EXISTS user_data;

DROP TABLE IF EXISTS user;
-- DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS empire;
DROP TABLE IF EXISTS building;
DROP TABLE IF EXISTS position;

-- -- USER_ROLE
-- CREATE TABLE role (
-- role_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- name VARCHAR(50),
-- PRIMARY KEY (role_id)
-- ) DEFAULT CHARSET=utf8mb4;

-- EMPIRE
CREATE TABLE empire (
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	PRIMARY KEY (id)
) DEFAULT CHARSET=utf8mb4;

-- -- VILLAGE_TYPE
-- CREATE TABLE village_type (
-- village_type_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- name VARCHAR(50),
-- PRIMARY KEY (village_type_id)
-- ) DEFAULT CHARSET=utf8mb4;

-- USER
CREATE TABLE user (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	password CHAR(128) NOT NULL,
	email VARCHAR(200) NOT NULL,
	playername VARCHAR(50) NOT NULL,
	enabled TINYINT(1) UNSIGNED DEFAULT 0,
	firstname VARCHAR(500), 
	surname VARCHAR(500), 
	age TINYINT UNSIGNED,
	gender CHAR(1),
	description VARCHAR(2000),
	role VARCHAR (50) NOT NULL,
	empire_id INTEGER UNSIGNED,
	PRIMARY KEY (id),
	FOREIGN KEY (empire_id) REFERENCES empire(id)	
) DEFAULT CHARSET=utf8mb4;

-- USER_ACTIVATION
CREATE TABLE user_activation (
   id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	token VARCHAR(500) NOT NULL,
	valid_to BIGINT UNSIGNED NOT NULL,
	user_id BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES user(id)
) DEFAULT CHARSET=utf8mb4;

-- -- USER_STORAGE
-- CREATE TABLE user_storage (
-- user_storage_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- actual TINYINT(1) UNSIGNED DEFAULT 1,
-- -- last_change DATE NOT NULL, TODO change time
-- data TEXT,
-- user_id BIGINT UNSIGNED NOT NULL,
-- PRIMARY KEY (user_storage_id),
-- FOREIGN KEY (user_id) REFERENCES user(user_id)
-- ) DEFAULT CHARSET=utf8mb4;
--
-- -- USER_PRODUCTION
-- CREATE TABLE user_production (
-- user_production_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- actual TINYINT(1) UNSIGNED DEFAULT 1,
-- production_data TEXT,
-- user_id BIGINT UNSIGNED NOT NULL,
-- PRIMARY KEY (user_production_id),
-- FOREIGN KEY (user_id) REFERENCES user(user_id)
-- ) DEFAULT CHARSET=utf8mb4;
--
-- -- USER_PRODUCTION
-- CREATE TABLE user_production (
-- user_production_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- actual TINYINT(1) UNSIGNED DEFAULT 1,
-- resources TEXT,
-- user_id BIGINT UNSIGNED NOT NULL,
-- PRIMARY KEY (user_production_id),
-- FOREIGN KEY (user_id) REFERENCES user(user_id)
-- ) DEFAULT CHARSET=utf8mb4;
--
-- -- USER_BUILDING
-- CREATE TABLE user_building (
-- user_building_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
-- -- record_date DATE NOT NULL,
--   actual TINYINT(1) UNSIGNED DEFAULT 1,
-- data TEXT,
-- user_id BIGINT UNSIGNED NOT NULL,
-- PRIMARY KEY (user_building_id),
-- FOREIGN KEY (user_id) REFERENCES user(user_id)
-- ) DEFAULT CHARSET=utf8mb4;

-- USER_DATA
CREATE TABLE user_data (
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   actual TINYINT(1) UNSIGNED DEFAULT 1,
	user_building_data TEXT,
	user_production_data TEXT,
	user_resource_data TEXT,
	user_id BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES user(id)
) DEFAULT CHARSET=utf8mb4;

-- BUILDING
CREATE TABLE building (
	building_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	village TINYINT(1) UNSIGNED,
	price text,
	duration BIGINT UNSIGNED,
	construct_time_coefficient FLOAT,
	max_builders INTEGER UNSIGNED,
	max_level TINYINT UNSIGNED,
	workers_growth SMALLINT UNSIGNED,
	max_value INTEGER UNSIGNED,
	value_type VARCHAR(50),
	restrictions text,
	PRIMARY KEY (building_id)
) DEFAULT CHARSET=utf8mb4;

-- POSITION
CREATE TABLE position (
   position_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
   village TINYINT(1) UNSIGNED DEFAULT 0,
   village_type VARCHAR(50),
   pos_x SMALLINT,
   pos_y SMALLINT,
   PRIMARY KEY (position_id)
) DEFAULT CHARSET=utf8mb4;

-- delete from role;
-- delete from empire;
--
-- INSERT into empire(id, name) values(1, 'Egyptian');
-- INSERT into empire(id, name) values(2, 'Babylonians');
-- INSERT into empire(id, name) values(3, 'Roman');
-- INSERT into empire(id, name) values(4, 'Assyrian');

-- INSERT into role(role_id, name) values(1, 'Admin');
-- INSERT into role(role_id, name) values(2, 'Player');


-- INSERT into user(password, email, playername, firstname, surname, age, gender, description, role_id, empire_id)
-- values('abrakadabrsa', 'abc@sey.cz', 'hrac', 'jmenoA', 'prijmenitA', 20, 'M', 'toto je popis', 2, 1);


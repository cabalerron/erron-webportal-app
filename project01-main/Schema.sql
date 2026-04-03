-- Project Name : PROJECT-01 Webportal
-- Date/Time    : 2024/10/14 08:20:00
-- Author       : PIC: Erron Monteverde
-- RDBMS Type   : PostgreSQL

-- Create Table User Master
CREATE SCHEMA IF NOT EXISTS project01;

DROP TABLE IF EXISTS project01.user_mst CASCADE;
CREATE TABLE project01.user_mst (
    user_id SERIAL UNIQUE,
    account_id VARCHAR(50) NOT NULL UNIQUE,
    associate_id VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    position_id INT,
    department_id INT,
    section_id INT,
    status_id INT,
    role_id INT,
    mailaddress VARCHAR(100) NOT NULL UNIQUE,
    invalid_try INT,
    last_login_date TIMESTAMP,
    lock_flag INT DEFAULT 0,
    reset_token VARCHAR(255),
    token_exp TIMESTAMP,
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table User Information user_id is foreign key constraint to user_mst
DROP TABLE IF EXISTS project01.user_info CASCADE;
CREATE TABLE project01.user_info (
    p_id SERIAL UNIQUE,
    user_id INT NOT NULL,
    first_name VARCHAR(50),
    middle_name VARCHAR(255),
    last_name VARCHAR(255),
    img_path VARCHAR(500),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table Role
DROP TABLE IF EXISTS project01.role_mst CASCADE;
CREATE TABLE project01.role_mst (
    role_id SERIAL UNIQUE,
    role_name VARCHAR(100) NOT NULL UNIQUE,
    role_sh_name VARCHAR(50),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for Department
DROP TABLE IF EXISTS project01.department_mst CASCADE;
CREATE TABLE project01.department_mst (
    department_id SERIAL UNIQUE,
    department_name VARCHAR(150) NOT NULL,
    department_sh_name VARCHAR(50),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for Business Unit/Section
DROP TABLE IF EXISTS project01.section_mst CASCADE;
CREATE TABLE project01.section_mst (
    section_id SERIAL UNIQUE,
    section_name VARCHAR(150) NOT NULL,
    section_sh_name VARCHAR(50),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for Position
DROP TABLE IF EXISTS project01.position_mst CASCADE;
CREATE TABLE project01.position_mst (
    position_id SERIAL UNIQUE,
    position_name VARCHAR(100) NOT NULL,
    position_sh_name VARCHAR(10),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for Employee Status/User Status
DROP TABLE IF EXISTS project01.user_status CASCADE;
CREATE TABLE project01.user_status (
    status_id SERIAL UNIQUE,
    status_name VARCHAR(150) NOT NULL,
    status_desc VARCHAR(150),
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for News
DROP TABLE IF EXISTS project01.news CASCADE;
CREATE TABLE project01.news (
    news_id SERIAL UNIQUE,
    title VARCHAR(200) NOT NULL,
    content TEXT,  
    image_path VARCHAR(200) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Table for Master Function
DROP TABLE IF EXISTS project01.function_mst CASCADE;
CREATE TABLE project01.function_mst (
    function_id SERIAL UNIQUE,
    function_code VARCHAR(50) NOT NULL UNIQUE,
    function_name VARCHAR(50) NOT NULL,
    module_code VARCHAR(50),
    tier INT,
    icon_id BIGINT,
    icon_url VARCHAR(500),
    display_index INT,
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create for Permission Table
DROP TABLE IF EXISTS project01.permission CASCADE;
CREATE TABLE project01.permission (
    permission_id SERIAL UNIQUE,
    function_code VARCHAR(50) NOT NULL UNIQUE,
    role_id INT,
    del_flag INT DEFAULT 0,
    create_id VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_id VARCHAR(50),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE ONLY project01.user_mst
    ADD CONSTRAINT user_mst_pk PRIMARY KEY (user_id);
ALTER TABLE ONLY project01.user_mst
    ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES project01.role_mst(role_id);

ALTER TABLE ONLY project01.user_info
    ADD CONSTRAINT user_info_pk PRIMARY KEY (p_id);
ALTER TABLE ONLY project01.user_info
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES project01.user_mst(user_id);

ALTER TABLE ONLY project01.role_mst
    ADD CONSTRAINT role_mst_pk PRIMARY KEY (role_id);

ALTER TABLE ONLY project01.department_mst
    ADD CONSTRAINT department_mst_pk PRIMARY KEY (department_id);

ALTER TABLE ONLY project01.section_mst
    ADD CONSTRAINT section_mst_pk PRIMARY KEY (section_id);

ALTER TABLE ONLY project01.position_mst
    ADD CONSTRAINT position_mst_pk PRIMARY KEY (position_id);

ALTER TABLE ONLY project01.user_status
    ADD CONSTRAINT user_status_pk PRIMARY KEY (status_id);

ALTER TABLE ONLY project01.news
    ADD CONSTRAINT news_pk PRIMARY KEY (news_id);

ALTER TABLE ONLY project01.function_mst
    ADD CONSTRAINT function_mst_pk PRIMARY KEY (function_id);

ALTER TABLE ONLY project01.permission
    ADD CONSTRAINT permission_pk PRIMARY KEY (permission_id);

-- Truncate tables to remove existing data
TRUNCATE TABLE project01.user_mst, 
                 project01.user_info, 
                 project01.role_mst, 
                 project01.department_mst, 
                 project01.section_mst, 
                 project01.position_mst, 
                 project01.user_status, 
                 project01.news, 
                 project01.function_mst, 
                 project01.permission 
CASCADE;

-- Insert sample data into role_mst
INSERT INTO project01.role_mst (role_name, role_sh_name, create_id, update_id)
VALUES 
('Admin', 'ADM', 'admin', 'admin'),
('User', 'USR', 'admin', 'admin');

-- Insert sample data into user_mst
INSERT INTO project01.user_mst (
    account_id,
    associate_id,
    password,
    position_id,
    department_id,
    section_id,
    status_id,
    role_id,
    mailaddress,
    invalid_try,
    last_login_date,
    lock_flag,
    reset_token,
    token_exp,
    del_flag,
    create_id,
    update_id
) VALUES
('admin', '1', '$2a$12$iwDnfp4t.xhxw5kfgSB6/.LXpVT7RjfaxDYFQ3whoNwCfSNwrpO9e', 1, 1, 1, 1, 1, 'admin@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('acsantos', '2', '$2a$12$zfquTivpUymZGJHlyfKFmOQVoR2QQI3v8BoDbb1gK31ybp4vYpP76', 1, 1, 1, 1, 2, 'ariescarlo15@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('smateo', '3', '$2a$12$w/TX1SxdxBc352sLY0ZnWOIqBiy6W/rVEeZ8op89yRaqmkFt0qcFK', 1, 1, 1, 1, 2, 'user2@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('gramirez', '4', '$2a$12$oAlCgEoSQFx8bvFleB46nOA0AsKJKBSO3PaRgH9P4IahODINifdjS', 1, 1, 1, 1, 2, 'user3@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('emonteverde', '5', '$2a$12$GItSCWL/SX2N3QI/gOGxr.DxmZc1H.4OsJwNL6z/xnB0JGztJeari', 1, 1, 1, 1, 2, 'user4@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('mgamay', '6', '$2a$12$bwvWgv1mw2CqbucmdTAwZuUeKBsKThCH0ZqbEdtC931eaQs5Sqi82', 1, 1, 1, 1, 2, 'user5@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin'),
('sserrano', '7', '$2a$12$DA0FqdIfShcbVhm8adRnA.rJyW.fruFVvUk9Us4uTs36zC/l2ASxW', 1, 1, 1, 1, 2, 'user6@example.com', 0, NULL, 0, NULL, NULL, 0, 'admin', 'admin');
    
-- Insert data into Functions Table
INSERT INTO project01.function_mst (
	function_id, 
	function_code, 
	function_name, 
	module_code, 
	tier, 
	create_id, 
	update_id, 
	update_date
) VALUES
(1, '11', 'User Management', NULL, 1, 1, NULL, NULL),
(2, '111', 'Registration', '11', 2, 1, NULL, NULL),
(3, '112', 'Edit', '11', 2, 1, NULL, NULL),
(4, '113', 'Delete', '11', 2, 1, NULL, NULL),
(5, '114', 'List View', '11', 2, 1, NULL, NULL),
(6, '115', 'User Profile Edit', '11', 2, 1, NULL, NULL),
(7, '12', 'News Management', NULL, 1, 1, NULL, NULL),
(8, '121', 'Registration', '12', 2, 1, NULL, NULL),
(9, '122', 'Edit', '12', 2, 1, NULL, NULL),
(10, '123', 'Delete', '12', 2, 1, NULL, NULL),
(11, '124', 'List View', '12', 2, 1, NULL, NULL),
(12, '125', 'Display', '12', 2, 1, NULL, NULL),
(13, '116', 'Unlock User', '11', 2, 1, NULL, NULL);

-- Insert data into Permission Table
INSERT INTO project01.permission (
	permission_id, 
	function_code, 
	role_id, 
	del_flag,
	create_id, 
	create_date, 
	update_id, 
	update_date
)VALUES 
(1, '111', 1, 0, '1', NULL, NULL, NULL),
(2, '112', 1, 0, '1', NULL, NULL, NULL),
(3, '113', 1, 0, '1', NULL, NULL, NULL),
(4, '114', 1, 0, '1', NULL, NULL, NULL),
(5, '115', 1, 0, '1', NULL, NULL, NULL),
(6, '124', 1, 0, '1', NULL, NULL, NULL),
(7, '125', 1, 0, '1', NULL, NULL, NULL);

ALTER TABLE project01.user_mst OWNER TO project01db;
ALTER TABLE project01.user_info OWNER TO project01db;
ALTER TABLE project01.role_mst OWNER TO project01db;
ALTER TABLE project01.department_mst OWNER TO project01db;
ALTER TABLE project01.section_mst OWNER TO project01db;
ALTER TABLE project01.position_mst OWNER TO project01db;
ALTER TABLE project01.user_status OWNER TO project01db;
ALTER TABLE project01.news OWNER TO project01db;
ALTER TABLE project01.function_mst OWNER TO project01db;
ALTER TABLE project01.permission OWNER TO project01db;

-- Comment for User Master Table
COMMENT ON TABLE project01.user_mst IS 'User Master';
COMMENT ON COLUMN project01.user_mst.user_id IS 'User ID';
COMMENT ON COLUMN project01.user_mst.account_id IS 'Account ID/Username';
COMMENT ON COLUMN project01.user_mst.associate_id IS 'Associate ID';
COMMENT ON COLUMN project01.user_mst.password IS 'Password';
COMMENT ON COLUMN project01.user_mst.position_id IS 'Position';
COMMENT ON COLUMN project01.user_mst.department_id IS 'Department';
COMMENT ON COLUMN project01.user_mst.section_id IS 'Business Unit';
COMMENT ON COLUMN project01.user_mst.status_id IS 'Employee Status';
COMMENT ON COLUMN project01.user_mst.role_id IS 'Role';
COMMENT ON COLUMN project01.user_mst.mailaddress IS 'Email Address';
COMMENT ON COLUMN project01.user_mst.invalid_try IS 'Invalid Request';
COMMENT ON COLUMN project01.user_mst.last_login_date IS 'Last Login Date';
COMMENT ON COLUMN project01.user_mst.lock_flag IS 'Lock Flag';
COMMENT ON COLUMN project01.user_mst.reset_token IS 'Reset Token';
COMMENT ON COLUMN project01.user_mst.token_exp IS 'Token Expiration';
COMMENT ON COLUMN project01.user_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.user_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.user_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.user_mst.update_id IS 'Updater';
COMMENT ON COLUMN project01.user_mst.update_date IS 'Update Date';

-- Comment for User Information Table
COMMENT ON TABLE project01.user_info IS 'User Information';
COMMENT ON COLUMN project01.user_info.p_id IS 'Info ID';
COMMENT ON COLUMN project01.user_info.user_id IS 'User ID';
COMMENT ON COLUMN project01.user_info.first_name IS 'First Name';
COMMENT ON COLUMN project01.user_info.middle_name IS 'Middle Name';
COMMENT ON COLUMN project01.user_info.last_name IS 'Last Name';
COMMENT ON COLUMN project01.user_info.img_path IS 'Profile Picture';
COMMENT ON COLUMN project01.user_info.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.user_info.create_id IS 'Registrant';
COMMENT ON COLUMN project01.user_info.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.user_info.update_id IS 'Updater';
COMMENT ON COLUMN project01.user_info.update_date IS 'Update Date';

-- Comment for Role Table
COMMENT ON TABLE project01.role_mst IS 'Role Master';
COMMENT ON COLUMN project01.role_mst.role_id IS 'Role ID';
COMMENT ON COLUMN project01.role_mst.role_name IS 'Role Name';
COMMENT ON COLUMN project01.role_mst.role_sh_name IS 'Role Short Name';
COMMENT ON COLUMN project01.role_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.role_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.role_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.role_mst.update_id IS 'Updater';
COMMENT ON COLUMN project01.role_mst.update_date IS 'Update Date';

-- Comment for Department Table
COMMENT ON TABLE project01.department_mst IS 'Department Master';
COMMENT ON COLUMN project01.department_mst.department_id IS 'Department ID';
COMMENT ON COLUMN project01.department_mst.department_name IS 'Department Name';
COMMENT ON COLUMN project01.department_mst.department_sh_name IS 'Department Short Name';
COMMENT ON COLUMN project01.department_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.department_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.department_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.department_mst.update_id IS 'Updater';

-- Comment for Section Table
COMMENT ON TABLE project01.section_mst IS 'Section Master';
COMMENT ON COLUMN project01.section_mst.section_id IS 'Section ID';
COMMENT ON COLUMN project01.section_mst.section_name IS 'Section Name';
COMMENT ON COLUMN project01.section_mst.section_sh_name IS 'Section Short Name';
COMMENT ON COLUMN project01.section_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.section_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.section_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.section_mst.update_id IS 'Updater';
COMMENT ON COLUMN project01.section_mst.update_date IS 'Update Date';

-- Comment for Position Table
COMMENT ON TABLE project01.position_mst IS 'Position Master';
COMMENT ON COLUMN project01.position_mst.position_id IS 'Position ID';
COMMENT ON COLUMN project01.position_mst.position_name IS 'Position Name';
COMMENT ON COLUMN project01.position_mst.position_sh_name IS 'Position Short Name';
COMMENT ON COLUMN project01.position_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.position_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.position_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.position_mst.update_id IS 'Updater';
COMMENT ON COLUMN project01.position_mst.update_date IS 'Update Date';

-- Comment for User Status Table
COMMENT ON TABLE project01.user_status IS 'User Status';
COMMENT ON COLUMN project01.user_status.status_id IS 'Status ID';
COMMENT ON COLUMN project01.user_status.status_name IS 'Status Name';
COMMENT ON COLUMN project01.user_status.status_desc IS 'Status Descriptions';
COMMENT ON COLUMN project01.user_status.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.user_status.create_id IS 'Registrant';
COMMENT ON COLUMN project01.user_status.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.user_status.update_id IS 'Updater';
COMMENT ON COLUMN project01.user_status.update_date IS 'Update Date';

-- Comment for News Table
COMMENT ON TABLE project01.news IS 'News';
COMMENT ON COLUMN project01.news.news_id IS 'News ID';
COMMENT ON COLUMN project01.news.title IS 'Title';
COMMENT ON COLUMN project01.news.news_content IS 'News Content';
COMMENT ON COLUMN project01.news.image_path IS 'Image Path';
COMMENT ON COLUMN project01.news.start_date IS 'Start Date';
COMMENT ON COLUMN project01.news.end_date IS 'End Date';
COMMENT ON COLUMN project01.news.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.news.create_id IS 'Registrant';
COMMENT ON COLUMN project01.news.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.news.update_id IS 'Updater';
COMMENT ON COLUMN project01.news.update_date IS 'Update Date';

-- Comment for Function Table
COMMENT ON TABLE project01.function_mst IS 'Master Function';
COMMENT ON COLUMN project01.function_mst.function_id IS 'Function ID';    
COMMENT ON COLUMN project01.function_mst.function_code IS 'Function Code';
COMMENT ON COLUMN project01.function_mst.function_name IS 'Function Name';
COMMENT ON COLUMN project01.function_mst.module_code IS 'Module Code';
COMMENT ON COLUMN project01.function_mst.tier IS 'Tier';
COMMENT ON COLUMN project01.function_mst.icon_id IS 'Icon ID';
COMMENT ON COLUMN project01.function_mst.icon_url IS 'Icon URL';
COMMENT ON COLUMN project01.function_mst.display_index IS 'Display Index';
COMMENT ON COLUMN project01.function_mst.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.function_mst.create_id IS 'Registrant';
COMMENT ON COLUMN project01.function_mst.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.function_mst.update_id IS 'Updater';
COMMENT ON COLUMN project01.function_mst.update_date IS 'Update Date';

-- Comment for Permission Table
COMMENT ON TABLE project01.permission IS 'Permission';
COMMENT ON COLUMN project01.permission.permission_id IS 'Permission ID';
COMMENT ON COLUMN project01.permission.function_id IS 'Function ID';
COMMENT ON COLUMN project01.permission.role_id IS 'Role ID';
COMMENT ON COLUMN project01.permission.del_flag IS 'Delete Flag';
COMMENT ON COLUMN project01.permission.create_id IS 'Registrant';
COMMENT ON COLUMN project01.permission.create_date IS 'Registration Date';
COMMENT ON COLUMN project01.permission.update_id IS 'Updater';
COMMENT ON COLUMN project01.permission.update_date IS 'Update Date';



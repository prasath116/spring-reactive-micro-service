CREATE TABLE IF NOT EXISTS Department (id BIGINT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(50) NOT NULL,college_id BIGINT);

INSERT INTO Department (name,college_id) VALUES ('CSE', 1);
INSERT INTO Department (name,college_id) VALUES ('EEE', 1);
INSERT INTO Department (name,college_id) VALUES ('CSE', 2);
INSERT INTO Department (name,college_id) VALUES ('ECE', 2);
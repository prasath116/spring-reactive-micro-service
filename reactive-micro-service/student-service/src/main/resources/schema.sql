CREATE TABLE IF NOT EXISTS Student (id BIGINT AUTO_INCREMENT PRIMARY KEY,
						name VARCHAR(50) NOT NULL,
						age INT,
						college_id BIGINT,
						department_id BIGINT);

INSERT INTO Student (name,age,college_id,department_id) VALUES ('aa', 18,1,1);
INSERT INTO Student (name,age,college_id,department_id) VALUES ('bb', 18,1,2);
INSERT INTO Student (name,age,college_id,department_id) VALUES ('cc', 18,2,1);
INSERT INTO Student (name,age,college_id,department_id) VALUES ('dd', 18,2,2);
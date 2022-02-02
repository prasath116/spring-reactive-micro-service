CREATE TABLE IF NOT EXISTS Employee (id BIGINT AUTO_INCREMENT PRIMARY KEY,
						name VARCHAR(50) NOT NULL,
						age INT,
						position VARCHAR(50),
						college_id BIGINT,
						department_id BIGINT);

INSERT INTO Employee (name,age,position,college_id,department_id) VALUES ('aa',35,'pos1', 1,1);
INSERT INTO Employee (name,age,position,college_id,department_id) VALUES ('bb',32,'pos2', 1,2);
INSERT INTO Employee (name,age,position,college_id,department_id) VALUES ('cc',35,'pos1', 2,1);
INSERT INTO Employee (name,age,position,college_id,department_id) VALUES ('dd',38,'pos2', 2,2);
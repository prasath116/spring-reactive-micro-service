CREATE TABLE IF NOT EXISTS College (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  address VARCHAR(500)
);


INSERT INTO College (name,address) VALUES ('CEG', 'Guindy, Chennai');
INSERT INTO College (name,address) VALUES ('MIT', 'Chrompet, Chennai');


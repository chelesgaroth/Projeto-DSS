CREATE DATABASE SistemaArmazem;

CREATE USER 'g19'@'localhost' IDENTIFIED BY 'G19.1234567';
GRANT ALL PRIVILEGES ON * . * TO 'g19'@'localhost';
FLUSH PRIVILEGES;
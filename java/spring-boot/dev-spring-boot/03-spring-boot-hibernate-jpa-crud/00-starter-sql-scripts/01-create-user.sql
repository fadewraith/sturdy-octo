-- Drop user first if they exist
DROP USER if exists 'springstudent'@'localhost' ;

-- Now create user with prop privileges
CREATE USER 'springstudent'@'localhost' IDENTIFIED BY 'my_sql';

GRANT ALL PRIVILEGES ON * . * TO 'springstudent'@'localhost';
CREATE USER 'myuser'@'%' IDENTIFIED WITH mysql_native_password BY 'mypassword';
GRANT ALL PRIVILEGES ON *.* TO 'myuser'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;


CREATE DATABASE IF NOT EXISTS ticketing;
USE ticketing;
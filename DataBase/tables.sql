create database if not exists chatBot;

use chatBot;

create table if not exists mensajes(
	id int primary key auto_increment,
    contenido text not null,
    hora_mensaje time not null
);

create table if not exists usuarios(
	id int primary key auto_increment,
    nombre varchar (100) not null,
    telefono int not null
    );

create table if not exists chats(
	id int primary key auto_increment,
    id_usuario int not null,
    fecha_inicio date not null,
    fecha_fin date not null,
    foreign key(id_usuario)references usuarios(id)
);

CREATE TABLE `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(100) NOT NULL,
    `telefono` BIGINT NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    UNIQUE KEY `username_unique` (`username`)
);

show tables;
create table usuarios (

    id serial primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null unique,
    login varchar(100) not null unique,
    senha varchar(255) not null ,
    tipo_usuario varchar(100) not null
);
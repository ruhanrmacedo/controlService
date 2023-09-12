create table tecnicos (

    idtecnico serial primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null unique,
    login varchar(7),
    placa varchar(7),
    dataadmissao DATE
);
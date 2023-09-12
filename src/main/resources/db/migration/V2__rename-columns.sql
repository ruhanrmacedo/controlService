-- Renomear a coluna idtecnico para id_tecnico
ALTER TABLE tecnicos
    RENAME COLUMN idtecnico TO id_tecnico;

-- Renomear a coluna cpf para cpf_numero
ALTER TABLE tecnicos
    RENAME COLUMN dataadmissao TO data_admissao;
-- Remover a chave primária atual
ALTER TABLE servicos_executados_servico_adicional DROP CONSTRAINT servicos_executados_servico_adicional_pkey;

-- Adicionar uma nova coluna de ID
ALTER TABLE servicos_executados_servico_adicional ADD COLUMN id SERIAL PRIMARY KEY;

-- Remover as restrições de chave estrangeira existentes, se existirem
ALTER TABLE servicos_executados_servico_adicional DROP CONSTRAINT IF EXISTS fk_servico_executado;
ALTER TABLE servicos_executados_servico_adicional DROP CONSTRAINT IF EXISTS fk_servico_adicional;

-- Recriar as chaves estrangeiras para garantir integridade referencial
ALTER TABLE servicos_executados_servico_adicional
    ADD CONSTRAINT fk_servico_executado
        FOREIGN KEY (servico_executado_id)
            REFERENCES servicos_executados (id)
            ON DELETE CASCADE;

ALTER TABLE servicos_executados_servico_adicional
    ADD CONSTRAINT fk_servico_adicional
        FOREIGN KEY (servico_adicional_id)
            REFERENCES servicos (id_servico)
            ON DELETE CASCADE;

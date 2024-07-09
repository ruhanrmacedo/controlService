-- V11__add_additional_service_columns.sql

-- Adicionar colunas na tabela servicos_executados
ALTER TABLE servicos_executados
    ADD COLUMN percentual_acao_final_de_semana DOUBLE PRECISION,
    ADD COLUMN valor_total DOUBLE PRECISION,
    ADD COLUMN bonificacao DOUBLE PRECISION;

-- Criar tabela de associação para múltiplos serviços adicionais
CREATE TABLE servicos_executados_servico_adicional (
                                                       servico_executado_id BIGINT NOT NULL,
                                                       servico_adicional_id BIGINT NOT NULL,
                                                       PRIMARY KEY (servico_executado_id, servico_adicional_id),
                                                       CONSTRAINT fk_servico_executado
                                                           FOREIGN KEY (servico_executado_id)
                                                               REFERENCES servicos_executados (id)
                                                               ON DELETE CASCADE,
                                                       CONSTRAINT fk_servico_adicional
                                                           FOREIGN KEY (servico_adicional_id)
                                                               REFERENCES servicos (id_servico)
                                                               ON DELETE CASCADE
);

-- Atualizar valor_total com base nos dados existentes
UPDATE servicos_executados SET valor_total = (
                                                 SELECT s.valor1 + COALESCE(SUM(sa.valor1), 0)
                                                 FROM servicos s
                                                          LEFT JOIN servicos_executados_servico_adicional sea ON s.id_servico = sea.servico_adicional_id
                                                          LEFT JOIN servicos sa ON sa.id_servico = sea.servico_adicional_id
                                                 WHERE s.id_servico = servicos_executados.servico_id
                                                 GROUP BY s.valor1
                                             ) + COALESCE(bonificacao, 0)
    + COALESCE(percentual_acao_final_de_semana, 0) * (
        SELECT s.valor1 + COALESCE(SUM(sa.valor1), 0)
        FROM servicos s
                 LEFT JOIN servicos_executados_servico_adicional sea ON s.id_servico = sea.servico_adicional_id
                 LEFT JOIN servicos sa ON sa.id_servico = sea.servico_adicional_id
        WHERE s.id_servico = servicos_executados.servico_id
        GROUP BY s.valor1
    ) / 100;

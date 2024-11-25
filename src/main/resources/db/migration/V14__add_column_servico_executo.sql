-- Adiciona duas novas colunas nome_cliente e metragem_cabo_drop
ALTER TABLE servicos_executados
    ADD COLUMN nome_cliente VARCHAR(255),
    ADD COLUMN metragem_cabo_drop DOUBLE PRECISION;

CREATE TABLE servicos_executados (
     id SERIAL PRIMARY KEY,
     contrato VARCHAR(255),
     os VARCHAR(255),
     data DATE,
     tecnico_id BIGINT,
     servico_id BIGINT,
     FOREIGN KEY (tecnico_id) REFERENCES tecnicos(id_tecnico),
     FOREIGN KEY (servico_id) REFERENCES servicos(id_servico)
);
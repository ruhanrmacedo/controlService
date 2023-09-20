CREATE TABLE servicos (

      id_servico SERIAL PRIMARY KEY,
      descricao VARCHAR(255) NOT NULL,
      valor_claro DOUBLE PRECISION NOT NULL,
      valor_macedo DOUBLE PRECISION NOT NULL,
      tipo_servico VARCHAR(50) NOT NULL
);
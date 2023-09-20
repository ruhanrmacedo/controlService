ALTER TABLE servicos ADD COLUMN ativo boolean;
UPDATE servicos SET ativo = true;
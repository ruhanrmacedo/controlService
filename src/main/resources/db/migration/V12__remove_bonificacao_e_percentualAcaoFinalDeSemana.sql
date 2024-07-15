-- V12__remove_bonificacao_e_percentualAcaoFinalDeSemana.sql

ALTER TABLE servicos_executados
DROP COLUMN bonificacao,
DROP COLUMN percentual_acao_final_de_semana;

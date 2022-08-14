
INSERT INTO grupo (id, descricao, nome) VALUES 
(1, 'usuario', 'usuario' );

INSERT INTO usuario (id, login, nome, senha,  ativo) VALUES
(1, 'admin', 'admin','fc7e8f6341fe27209b8e111bc7408fbc',true);

INSERT INTO usuario_grupo (usuarios_id, grupos_id) VALUES
(1, 1);

CREATE OR REPLACE VIEW v_usuario_grupo AS
SELECT u.login AS username, g.nome AS grupo, u.senha AS senha
FROM usuario u, grupo g, usuario_grupo ug
WHERE u.id = ug.usuarios_id AND g.id = ug.grupos_id 
and u.ativo = true;

alter table contrato ADD column valorRestante numeric(10,2);

alter table mensagem add column escritor_id bigint;
ALTER TABLE mensagem ADD CONSTRAINT fk_escritor_id FOREIGN KEY (escritor_id) REFERENCES usuario (id);

alter table Acrescimo ADD column ativo boolean;

alter table notaFiscal add column unidadeOrganizacional_id bigint;
ALTER TABLE notaFiscal ADD CONSTRAINT fk_unidadeOrganizacional_id FOREIGN KEY (unidadeOrganizacional_id) REFERENCES unidadeOrganizacional (id);

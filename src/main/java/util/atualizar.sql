
INSERT INTO grupo (id, descricao, nome) VALUES 
(1, 'usuario', 'usuario' );
INSERT INTO grupo (id, descricao, nome) VALUES 
(1, 'gestor', 'gestor' );

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

alter table Chat ADD column lidoReceptor boolean;
alter table Chat ADD column lidoEmissor boolean;

alter table contrato add column tipoContrato varchar(250);

alter table contrato add column tipoFiscalizacao varchar(250);


alter table contrato add column tipoLicitacao varchar(250);

alter table contratado add column rg varchar(250);
alter table notaFiscal add column dataExtrato varchar(250);
alter table contratado add column inscricaoEstadual varchar(250);

alter table contrato add column fiscal_id bigint;
ALTER TABLE contrato ADD CONSTRAINT fk_fiscal_id FOREIGN KEY (fiscal_id) REFERENCES usuario (id);


alter table notaFiscal add column lotacao varchar(250);
alter table notaFiscal add column atoDesignacao varchar(250);
alter table notaFiscal add column apartir varchar(250);
alter table notaFiscal add column periodoFiscalizado varchar(250);
alter table notaFiscal add column obrigacaoMensal boolean default false;
alter table notaFiscal add column prazoEstabelecido boolean default false;

alter table notaFiscal add column documentoObrigatorio boolean default false;
alter table notaFiscal add column relatorio boolean default false;
alter table notaFiscal add column qualidadeEsperada boolean default false;
alter table notaFiscal add column informouSituacao boolean default false;
alter table notaFiscal add column diligenciaNecessarias boolean default false;

alter table notaFiscal add column inicioFiscalizado varchar(250);
alter table notaFiscal add column finalFiscalizado varchar(250);

alter table usuario add column cargo varchar(250);

alter table endereco add column logradouro varchar(250);

alter table notaFiscal add column numero numeric(10,2);

alter table notaFiscal add column garantiaContratual varchar(250);

alter table notaFiscal add column obrigacaoMensalSeAplica boolean default true;

alter table notaFiscal add column prazoEstabelecidoSeAplica boolean default true;

alter table notaFiscal add column documentoObrigatorioSeAplica boolean default true;

alter table notaFiscal add column relatorioSeAplica boolean default true;

alter table notaFiscal add column qualidadeEsperadaSeAplica boolean default true;

alter table notaFiscal add column informouSituacaoSeAplica boolean default true;

alter table notaFiscal add column diligenciaNecessariasSeAplica boolean default true;

alter table contrato drop column objetoContrato ;

alter table contrato add column objetoContrato varchar(250);

alter table contrato drop column NumeroProcesso ;

alter table contrato add column NumeroProcesso varchar(250);


alter table contrato drop column NumeroLicitacao ;

alter table contrato add column NumeroLicitacao varchar(250);

alter table setor add column usuarioResponsavel_id bigint;
ALTER TABLE setor ADD CONSTRAINT fk_usuarioResponsavel_id FOREIGN KEY (usuarioResponsavel_id) REFERENCES usuario (id);

alter table contrato drop column NumeroContrato ;

alter table contrato add column NumeroContrato varchar(250);

alter table contratado add column numeroConta bigint;

alter table contratado add column digito bigint;

alter table contratado add column operacao bigint;

alter table contratado add column agencia bigint;

alter table contratado add column banco varchar(250);

alter table contratado add column saldoInicial numeric(10,2);




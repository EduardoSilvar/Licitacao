
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

ALTER TABLE contratado ALTER COLUMN agencia TYPE varchar(250);


ALTER TABLE contrato ALTER COLUMN agencia TYPE varchar(250);



alter table notaFiscal drop column obrigacaoMensal ;
alter table notaFiscal drop column prazoEstabelecido ;
alter table notaFiscal drop column documentoObrigatorio ;
alter table notaFiscal drop column relatorio ;
alter table notaFiscal drop column qualidadeEsperada;
alter table notaFiscal drop column informouSituacao ;
alter table notaFiscal drop column diligenciaNecessarias ;


alter table notaFiscal add column obrigacaoMensal varchar(250);
alter table notaFiscal add column prazoEstabelecido varchar(250);
alter table notaFiscal add column documentoObrigatorio varchar(250);
alter table notaFiscal add column relatorio varchar(250);
alter table notaFiscal add column qualidadeEsperada varchar(250);
alter table notaFiscal add column informouSituacao varchar(250);
alter table notaFiscal add column diligenciaNecessarias varchar(250);




alter table relatorio add column logo_id bigint;
ALTER TABLE relatorio ADD CONSTRAINT fk_logo_id FOREIGN KEY (logo_id) REFERENCES anexo (id);

alter table relatorio add column margemDireitaCabecalho int;
alter table relatorio add column margemEsquerdaCabecalho int;
alter table relatorio add column margemDireitaRodape int;
alter table relatorio add column margemEsquerdaRodape int;
alter table relatorio add column fontSizeRodape int;
alter table relatorio add column fontSizeCabecalho int;
alter table relatorio add column posicaoImagemCabecalho boolean default true;
alter table relatorio add column posicaoImagemrodape boolean default true;
alter table relatorio add column ativo boolean default true;



alter table relatorio add column urlImagem varchar(250);
alter table relatorio add column urlImagemrodape varchar(250);
alter table relatorio add column rodape varchar(250);
alter table relatorio add column cabecalho varchar(250);

alter table relatorio add column corRodape varchar(250);
alter table relatorio add column corCabecalho varchar(250);

alter table unidadeorganizacional add column configuracao_id bigint;
ALTER TABLE unidadeorganizacional ADD CONSTRAINT fk_configuracao_id FOREIGN KEY (configuracao_id) REFERENCES configuracao (id);


alter table notaFiscal add column notificacaoExtrajudicial varchar(250);
alter table notaFiscal add column procedimentoPenalizacao varchar(250);
alter table notaFiscal add column suspensaoParalizacao varchar(250);
alter table notaFiscal add column necessidadeRescisao varchar(250);

alter table configuracao add column cabecalhoNotaFiscal_id bigint;
ALTER TABLE configuracao ADD CONSTRAINT fk_cabecalhoNotaFiscal_id FOREIGN KEY (cabecalhoNotaFiscal_id) REFERENCES cabecalhorodape (id);

alter table configuracao add column rodapeNotaFiscal_id bigint;
ALTER TABLE configuracao ADD CONSTRAINT fk_rodapeNotaFiscal_id FOREIGN KEY (rodapeNotaFiscal_id) REFERENCES cabecalhorodape (id);

alter table notaFiscal add column observacoes varchar(250);

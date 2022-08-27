# controle-votacao
Realiza controle de votação dos associados

Esse sistema tem a finalidade de controler votações em pautas, podendo abrir várias sessões por pauta, onde cada associado poderá efetuar unicamente 1 voto por pauta,
e no final obterá o resultado da votação.

Cobertura e regras do sistema:
-> Efetua cadastro de quantos associados desejar
-> Efetua cadastro de quantas pautas desejar
-> As pautas nascem com o status ABERTA
-> Existe um serviço para listar todas as pautas cadastradas em base
-> Existe um serviço para listar todas as pautas que contenha uma sessão aberta
-> Só poderá abrir sessão com o status da pauta ABERTA e se não tiver uma sessão já aberta para a mesma pauta
-> O associado só poderá votar uma unica vez por pauta e somente se tiver uma sessão aberta para a pauta
-> Só poderá votar associado cadastrado no sistema e que contanha permissão para votação(validada por sistema externo)
-> Votação consistem em SIM/NAO
-> Existe um serviço para finalizar a pauta e calcular os votos dando o resultado da votação para a pauta
-> Somente será possivel finalizar a pauta se não tiver sessão mais aberta e se não tiver dado empate na votação
-> Ao finalizar a pauta, o status muda para FINALIZADA e agora poderemos ver o resultado das votações na pauta(poderá ser visto na listagem das pautas)


Informações técnicas:
-> Base de dados em postgresql 14.11
-> Rodar o script para criação 
-> configurações da base como PORTA, USER e PASS poderá ser vista logo abaixo do script de banco
-> Sistema implementado em Java 17, SpringBoot 2.7.3, Maven
-> Aplicação conta com documentação swagger: http://{dominio}/apicontrole/swagger-ui.html
  caso execute local
  http://localhost:8080/apicontrole/swagger-ui.html


SCRIPT:
create database controle_votacao;

create sequence public.seq_associado_id
	minvalue 1
	start with 1
	increment by 1
	no cycle
	cache 1;

create sequence public.seq_pauta_id
	minvalue 1
	start with 1
	increment by 1
	no cycle
	cache 1;

create sequence public.seq_sessao_id
	minvalue 1
	start with 1
	increment by 1
	no cycle
	cache 1;

create sequence public.seq_votacao_id
	minvalue 1
	start with 1
	increment by 1
	no cycle
	cache 1;

create table public.tb_associado (
	id_associado int8 primary key,
	cpf varchar(11) not null unique,
	nome varchar(100) not null
);

create table public.tb_pauta (
	id_pauta int8 primary key,
	nome varchar(100) not null,
	status varchar(10) not null,
	resultado varchar(3)
);

create table public.tb_sessao (
	id_sessao int8 primary key,
	id_pauta int8 not null,
	dh_inicio timestamp not null,
	dh_fim timestamp not null,
	foreign key (id_pauta) references public.tb_pauta (id_pauta)
);

create table public.tb_votacao (
	id_votacao int8 primary key,
	id_pauta int8 not null,
	id_sessao int8 not null,
	id_associado int8 not null,
	voto varchar(3) not null,
	foreign key (id_pauta) references public.tb_pauta (id_pauta),
	foreign key (id_sessao) references public.tb_sessao (id_sessao),
	foreign key (id_associado) references public.tb_associado (id_associado)
);

configs
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    password: satellite
    url: jdbc:postgresql://localhost:5432/controle_votacao
    username: postgres

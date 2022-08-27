# controle-votacao
Realiza controle de votação dos associados
<br>
<br>
Esse sistema tem a finalidade de controler votações em pautas, podendo abrir várias sessões por pauta, onde cada associado poderá efetuar unicamente 1 voto por pauta,
e no final obterá o resultado da votação.
<br>
<br>
Cobertura e regras do sistema:<br>
-> Efetua cadastro de quantos associados desejar<br>
-> Efetua cadastro de quantas pautas desejar<br>
-> As pautas nascem com o status ABERTA<br>
-> Existe um serviço para listar todas as pautas cadastradas em base<br>
-> Existe um serviço para listar todas as pautas que contenham uma sessão aberta<br>
-> Só poderá abrir sessão com o status da pauta ABERTA e se não tiver uma sessão já aberta para a mesma pauta<br>
-> O associado só poderá votar uma única vez por pauta e somente se tiver uma sessão aberta para a pauta<br>
-> Só poderá votar associado cadastrado no sistema e que contanha permissão para votação(validada por sistema externo)<br>
-> Votação consiste em SIM/NAO<br>
-> Existe um serviço para finalizar a pauta e calcular os votos dando o resultado da votação para a pauta<br>
-> Somente será possivel finalizar a pauta, se não tiver sessão mais aberta e se não tiver dado empate na votação<br>
-> Ao finalizar a pauta, o status muda para FINALIZADA e agora poderemos ver o resultado das votações na pauta(poderá ser visto na listagem das pautas)<br>
<br>
<br>
Informações técnicas:<br>
-> Base de dados em postgresql 14.11<br>
-> Rodar o script para criação <br>
-> configurações da base como PORTA, USER e PASS poderá ser vista logo abaixo do script de banco<br>
-> Sistema implementado em Java 17, SpringBoot 2.7.3, Maven<br>
-> Aplicação conta com documentação swagger: http://{dominio}/apicontrole/swagger-ui.html<br>
  caso execute local<br>
  http://localhost:8080/apicontrole/swagger-ui.html<br>
<br>
<br>
SCRIPT:<br>
create database controle_votacao;<br>
<br>
create sequence public.seq_associado_id<br>
	minvalue 1<br>
	start with 1<br>
	increment by 1<br>
	no cycle<br>
	cache 1;<br>
<br>
create sequence public.seq_pauta_id<br>
	minvalue 1<br>
	start with 1<br>
	increment by 1<br>
	no cycle<br>
	cache 1;<br>
<br>
create sequence public.seq_sessao_id<br>
	minvalue 1<br>
	start with 1<br>
	increment by 1<br>
	no cycle<br>
	cache 1;<br>
<br>
create sequence public.seq_votacao_id<br>
	minvalue 1<br>
	start with 1<br>
	increment by 1<br>
	no cycle<br>
	cache 1;<br>
<br>
create table public.tb_associado (<br>
	id_associado int8 primary key,<br>
	cpf varchar(11) not null unique,<br>
	nome varchar(100) not null<br>
);<br>
<br>
create table public.tb_pauta (<br>
	id_pauta int8 primary key,<br>
	nome varchar(100) not null,<br>
	status varchar(10) not null,<br>
	resultado varchar(3)<br>
);<br>
<br>
create table public.tb_sessao (<br>
	id_sessao int8 primary key,<br>
	id_pauta int8 not null,<br>
	dh_inicio timestamp not null,<br>
	dh_fim timestamp not null,<br>
	foreign key (id_pauta) references public.tb_pauta (id_pauta)<br>
);<br>
<br>
create table public.tb_votacao (<br>
	id_votacao int8 primary key,<br>
	id_pauta int8 not null,<br>
	id_sessao int8 not null,<br>
	id_associado int8 not null,<br>
	voto varchar(3) not null,<br>
	foreign key (id_pauta) references public.tb_pauta (id_pauta),<br>
	foreign key (id_sessao) references public.tb_sessao (id_sessao),<br>
	foreign key (id_associado) references public.tb_associado (id_associado)<br>
);<br>
<br>
configs<br>
spring:<br>
  datasource:<br>
    driver-class-name: org.postgresql.Driver<br>
    password: satellite<br>
    url: jdbc:postgresql://localhost:5432/controle_votacao<br>
    username: postgres<br>

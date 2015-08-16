# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table amigo (
  id                        bigint auto_increment not null,
  id_usuario                bigint,
  id_amigo                  bigint,
  hora                      datetime(6),
  ativo                     tinyint(1) default 0,
  constraint pk_amigo primary key (id))
;

create table evento (
  id                        bigint auto_increment not null,
  id_usuario                bigint,
  nome                      varchar(255),
  descricao                 varchar(255),
  cidade                    varchar(255),
  estado                    varchar(255),
  constraint pk_evento primary key (id))
;

create table grupo (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  descricao                 varchar(255),
  url_imagem                varchar(255),
  ativo                     tinyint(1) default 0,
  publico                   tinyint(1) default 0,
  constraint pk_grupo primary key (id))
;

create table pessoa (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  url_imagem                varchar(255),
  sexo                      varchar(255),
  cidade                    varchar(255),
  estado                    varchar(255),
  constraint pk_pessoa primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  id_usuario                bigint,
  conteudo                  varchar(255),
  image_url                 varchar(255),
  hora                      datetime(6),
  ativo                     tinyint(1) default 0,
  constraint pk_post primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table amigo;

drop table evento;

drop table grupo;

drop table pessoa;

drop table post;

SET FOREIGN_KEY_CHECKS=1;


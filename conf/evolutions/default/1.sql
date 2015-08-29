# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comentario (
  id                        bigint auto_increment not null,
  texto                     varchar(180) not null,
  ativo                     tinyint(1) default 0 not null,
  criador_id                bigint not null,
  post_id                   bigint not null,
  constraint pk_comentario primary key (id))
;

create table evento (
  id                        bigint auto_increment not null,
  nome                      varchar(80) not null,
  descricao                 longtext,
  data                      datetime(6) not null,
  local                     varchar(255),
  criador_id                bigint not null,
  constraint pk_evento primary key (id))
;

create table grupo (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  ativo                     tinyint(1) default 0,
  publico                   tinyint(1) default 0,
  criador_id                bigint not null,
  constraint pk_grupo primary key (id))
;

create table pessoa (
  id                        bigint auto_increment not null,
  nome                      varchar(80) not null,
  email                     varchar(50) not null,
  senha                     varchar(30) not null,
  oauth_provider            varchar(255),
  oauth_id                  varchar(255),
  url_imagem                varchar(255),
  sexo                      varchar(1) not null,
  cidade                    varchar(80),
  estado                    varchar(30),
  constraint uq_pessoa_1 unique (email),
  constraint pk_pessoa primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  conteudo                  varchar(180) not null,
  postado_em                datetime(6) not null,
  ativo                     tinyint(1) default 0 not null,
  criador_id                bigint not null,
  grupo_id                  bigint,
  constraint pk_post primary key (id))
;


create table evento_participantes (
  idEvento                       bigint not null,
  idParticipante                 bigint not null,
  constraint pk_evento_participantes primary key (idEvento, idParticipante))
;

create table grupo_participantes (
  idGrupo                        bigint not null,
  idParticipante                 bigint not null,
  constraint pk_grupo_participantes primary key (idGrupo, idParticipante))
;

create table amigo (
  idPessoa                       bigint not null,
  idAmigo                        bigint not null,
  constraint pk_amigo primary key (idPessoa, idAmigo))
;

create table posts_curtidas (
  idPessoa                       bigint not null,
  idPost                         bigint not null,
  constraint pk_posts_curtidas primary key (idPessoa, idPost))
;
alter table comentario add constraint fk_comentario_criador_1 foreign key (criador_id) references pessoa (id) on delete restrict on update restrict;
create index ix_comentario_criador_1 on comentario (criador_id);
alter table comentario add constraint fk_comentario_post_2 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comentario_post_2 on comentario (post_id);
alter table evento add constraint fk_evento_criador_3 foreign key (criador_id) references pessoa (id) on delete restrict on update restrict;
create index ix_evento_criador_3 on evento (criador_id);
alter table grupo add constraint fk_grupo_criador_4 foreign key (criador_id) references pessoa (id) on delete restrict on update restrict;
create index ix_grupo_criador_4 on grupo (criador_id);
alter table post add constraint fk_post_criador_5 foreign key (criador_id) references pessoa (id) on delete restrict on update restrict;
create index ix_post_criador_5 on post (criador_id);
alter table post add constraint fk_post_grupo_6 foreign key (grupo_id) references grupo (id) on delete restrict on update restrict;
create index ix_post_grupo_6 on post (grupo_id);



alter table evento_participantes add constraint fk_evento_participantes_evento_01 foreign key (idEvento) references evento (id) on delete restrict on update restrict;

alter table evento_participantes add constraint fk_evento_participantes_evento_02 foreign key (idParticipante) references evento (id) on delete restrict on update restrict;

alter table grupo_participantes add constraint fk_grupo_participantes_grupo_01 foreign key (idGrupo) references grupo (id) on delete restrict on update restrict;

alter table grupo_participantes add constraint fk_grupo_participantes_pessoa_02 foreign key (idParticipante) references pessoa (id) on delete restrict on update restrict;

alter table amigo add constraint fk_amigo_pessoa_01 foreign key (idPessoa) references pessoa (id) on delete restrict on update restrict;

alter table amigo add constraint fk_amigo_pessoa_02 foreign key (idAmigo) references pessoa (id) on delete restrict on update restrict;

alter table posts_curtidas add constraint fk_posts_curtidas_pessoa_01 foreign key (idPessoa) references pessoa (id) on delete restrict on update restrict;

alter table posts_curtidas add constraint fk_posts_curtidas_pessoa_02 foreign key (idPost) references pessoa (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comentario;

drop table evento;

drop table evento_participantes;

drop table grupo;

drop table grupo_participantes;

drop table pessoa;

drop table amigo;

drop table posts_curtidas;

drop table post;

SET FOREIGN_KEY_CHECKS=1;


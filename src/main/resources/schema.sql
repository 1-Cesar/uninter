--create schema blog;

create table usuario
(
    id_usuario 			numeric not null,
    nome       			text    not null,
    profissao  			text    not null,
    telefone   			text    not null,
    tipo			 	text    not null,
    login       		text    not null,
    senha      			text    not null,
    data_nascimento		date    not null,
    primary key (id_usuario)
);

create sequence seq_usuario
    increment 1
start 1;

create table tema
(
    id_tema 			numeric not null,
    id_usuario	 		numeric not null,
    nome    			text    not null,
    primary key (id_tema),
    constraint fk_tema_usuario
        foreign KEY (id_usuario)
            references usuario (id_usuario) on delete cascade
);

create sequence seq_tema
    increment 1
start 1;

create table postagem
(
    id_postagem 			numeric 	not null,
    id_usuario		 		numeric 	not null,
    titulo    				text    	not null,
    texto    				text    	not null,
    data_postagem    		timestamp   not null,
    primary key (id_postagem),
    constraint fk_postagem_usuario
        foreign KEY (id_usuario)
            references usuario (id_usuario) on delete cascade
);

create sequence seq_postagem
    increment 1
start 1;

create table cargo (
                       id_cargo numeric not null,
                       nome text unique not null,
                       primary key(id_cargo)
);

create sequence seq_cargo
    increment 1
start 1;

insert into cargo (id_cargo, nome)
values (nextval('seq_cargo'), 'ROLE_ADMIN');

insert into cargo (id_cargo, nome)
values (nextval('seq_cargo'), 'ROLE_USUARIO');

create table usuario_cargo (
                               id_usuario numeric not null,
                               id_cargo numeric not null,
                               primary key(id_usuario, id_cargo),
                               constraint fk_usuario_cargo_cargo
                                   foreign key (id_cargo)
                                       references cargo (id_cargo) on delete cascade,
                               constraint fk_usuario_cargo_usuario
                                   foreign key (id_usuario)
                                       references usuario (id_usuario) on delete cascade
);

create table postagem_tema (
                               id_postagem numeric not null,
                               id_tema		numeric not null,
                               primary key(id_postagem, id_tema),
                               constraint fk_postagem_tema
                                   foreign key (id_postagem)
                                       references postagem (id_postagem) on delete cascade,
                               constraint fk_postagem_tema_tema
                                   foreign key (id_tema)
                                       references tema (id_tema) on delete cascade
);

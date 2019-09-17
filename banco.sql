create table usuarios (
	id int not null primary key,
    login varchar(50) not null unique,
	senha varchar(100) not null
);
insert into usuarios (id, login, senha)
values (1, 'admin', '7288e2461e79f7347a2596d93a5d598ae61d786b'),
	   (2, 'samuel', '3f583a54949f5b43223c81315b80efb1b3dd160d');
create table tarefas (
	id serial not null primary key,
	descricao varchar(500) not null,
	criada_em timestamp with time zone not null,
	usuario_id int not null,

	constraint fk_tarefas_usuario
	  foreign key (usuario_id)
	  references usuarios (id)
);
insert into tarefas (descricao, created_at, usuario_id)
values ('Excluir o usuário "samuel".', '2005-03-18 11:59:58', 1),
		('Excluir o usuário "admin".', '2005-03-18 11:59:59', 2);
-- select * from usuarios;
-- select * from tarefas;

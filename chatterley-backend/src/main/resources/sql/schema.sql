create table users (
  id varchar(32) primary key,
  nickname varchar(200) not null,
  password_hash varchar(200) not null,
  create_time bigint not null,
  constraint uq_user_nickname unique (nickname)
);

create table if not exists USERS
(
    id    identity        not null primary key,
    first_name  varchar(255) not null,
    last_name  varchar(255) not null,
    middle_name  varchar(255) not null,
    age  int not null,
    email varchar(512) not null,
    created timestamp default now(),
    unique (email)
);
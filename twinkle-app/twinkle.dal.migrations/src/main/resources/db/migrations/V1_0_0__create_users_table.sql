CREATE TABLE "users" (
    id_user serial,
    login varchar(20) not null,
    password varchar(64) not null,
    role boolean not null,
    primary key (id_user)
);
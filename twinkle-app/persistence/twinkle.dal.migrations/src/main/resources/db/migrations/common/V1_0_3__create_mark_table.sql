CREATE TABLE "mark" (
    id_mark serial,
    mark_name varchar(100) not null,
    id_country int not null,
    primary key (id_mark)
);
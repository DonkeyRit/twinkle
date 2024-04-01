CREATE TABLE "model" (
    id_model serial,
    model_name varchar(200) not null,
    id_mark int not null,
    id_body_type int not null,
    primary key (id_model)
);
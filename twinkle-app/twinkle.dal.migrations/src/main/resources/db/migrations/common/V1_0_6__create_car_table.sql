CREATE TABLE "car" (
    id serial,
    model_year timestamp not null,
    id_model int not null,
    info text not null,
    image int not null,
    cost decimal not null,
    primary key (id)
);
CREATE TABLE "rent" (
    id_rent serial,
    id_client int not null,
    id_car int not null,
    start_date timestamp not null,
    plan_date timestamp not null,
    end_date timestamp,
    primary key (id_rent)
);

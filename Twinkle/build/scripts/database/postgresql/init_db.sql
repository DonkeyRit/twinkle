CREATE TABLE "user" (
    id_user serial,
    login varchar(20) not null,
    password varchar(64) not null,
    role boolean not null,
    primary key (id_user)
);

CREATE TABLE "client" (
    id_client serial,
    first_name varchar(100) not null,
    second_name varchar(100) not null,
    middle_name varchar(100) not null,
    address varchar(200) not null,
    phone_number varchar(12) not null,
    id_user int not null,
    primary key (id_client)
);

CREATE TABLE "country" (
    id_country serial,
    country_name varchar(100) not null,
    primary key (id_country)
);

CREATE TABLE "mark" (
    id_mark serial,
    mark_name varchar(100) not null,
    id_country int not null,
    primary key (id_mark)
);

CREATE TABLE "body_type" (
    id_body_type serial,
    body_type_name varchar(50) not null,
    primary key (id_body_type)
);

CREATE TABLE "model" (
    id_model serial,
    model_name varchar(200) not null,
    id_mark int not null,
    id_body_type int not null,
    primary key (id_model)
);

CREATE TABLE "car" (
    id_car serial,
    model_year timestamp not null,
    id_model int not null,
    info text not null,
    image int not null,
    cost decimal not null,
    primary key (id_car)
);

CREATE TABLE "rent" (
    id_rent serial,
    id_client int not null,
    id_car int not null,
    start_date timestamp not null,
    plan_date timestamp not null,
    end_date timestamp,
    primary key (id_rent)
);

CREATE TABLE "injury" (
    id_injury serial,
    injury_name varchar(200) not null,
    primary key (id_injury)
);

CREATE TABLE "resulting_injury" (
    id_resulting_injury serial,
    id_rent int not null,
    id_injury int not null,
    primary key (id_resulting_injury)
);

ALTER TABLE "client" ADD CONSTRAint Client_fk0 FOREIGN KEY (id_user) REFERENCES "user"(id_user);
ALTER TABLE "car" ADD CONSTRAint Car_fk0 FOREIGN KEY (id_model) REFERENCES "model"(id_model);
ALTER TABLE "model" ADD CONSTRAint Model_fk0 FOREIGN KEY (id_mark) REFERENCES "mark"(id_mark);
ALTER TABLE "model" ADD CONSTRAint Model_fk1 FOREIGN KEY (id_body_type) REFERENCES "body_type"(id_body_type);
ALTER TABLE "mark" ADD CONSTRAint Mark_fk0 FOREIGN KEY (id_country) REFERENCES "country"(id_country);
ALTER TABLE "rent" ADD CONSTRAint Rent_fk0 FOREIGN KEY (id_client) REFERENCES "client"(id_client);
ALTER TABLE "rent" ADD CONSTRAint Rent_fk1 FOREIGN KEY (id_car) REFERENCES "car"(id_car);
ALTER TABLE "resulting_injury" ADD CONSTRAint ResultingInjury_fk0 FOREIGN KEY (id_rent) REFERENCES "rent"(id_rent);
ALTER TABLE "resulting_injury" ADD CONSTRAint ResultingInjury_fk1 FOREIGN KEY (id_injury) REFERENCES "injury"(id_injury);


CREATE TABLE "clients" (
    id_client serial,
    first_name varchar(100) not null,
    second_name varchar(100) not null,
    middle_name varchar(100) not null,
    address varchar(200) not null,
    phone_number varchar(12) not null,
    id_user int not null,
    primary key (id_client)
);
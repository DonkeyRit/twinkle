CREATE TABLE "resulting_injury" (
    id_resulting_injury serial,
    id_rent int not null,
    id_injury int not null,
    primary key (id_resulting_injury)
);
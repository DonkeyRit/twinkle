CREATE DATABASE CarRental;
USE CarRental;

CREATE TABLE User (
	idUser INT NOT NULL AUTO_INCREMENT,
	login varchar(20) NOT NULL,
	password varchar(64) NOT NULL,
	role boolean NOT NULL,
	PRIMARY KEY (idUser)
);

CREATE TABLE Client (
	idClient INT NOT NULL AUTO_INCREMENT,
	firstName varchar(100) NOT NULL,
	secondName varchar(100) NOT NULL,
	Patronimic varchar(100) NOT NULL,
	address varchar(200) NOT NULL,
	phoneNumber varchar(12) NOT NULL,
	idUser INT NOT NULL,
	PRIMARY KEY (idClient)
);

CREATE TABLE Car (
	idCar INT NOT NULL AUTO_INCREMENT,
	modelYear DATETIME NOT NULL,
	idModel INT NOT NULL,
	info text NOT NULL,
	image INT NOT NULL,
	cost DECIMAL NOT NULL,
	PRIMARY KEY (idCar)
);

CREATE TABLE Model (
	idModel INT NOT NULL AUTO_INCREMENT,
	modelName varchar(200) NOT NULL,
	idMark INT NOT NULL,
	idBodyType INT NOT NULL,
	PRIMARY KEY (idModel)
);

CREATE TABLE Mark (
	idMark INT NOT NULL AUTO_INCREMENT,
	markName varchar(100) NOT NULL,
	idCountry INT NOT NULL,
	PRIMARY KEY (idMark)
);

CREATE TABLE BodyType (
	idBodyType INT NOT NULL AUTO_INCREMENT,
	bodyTypeName varchar(50) NOT NULL,
	PRIMARY KEY (idBodyType)
);

CREATE TABLE Country (
	idCountry INT NOT NULL AUTO_INCREMENT,
	nameCountry varchar(100) NOT NULL,
	PRIMARY KEY (idCountry)
);

CREATE TABLE Renta (
	idRenta INT NOT NULL AUTO_INCREMENT,
	idClient INT NOT NULL,
	idCar INT NOT NULL,
	dataStart DATETIME NOT NULL,
	dataPlan DATETIME NOT NULL,
	dataEnd DATETIME,
	PRIMARY KEY (idRenta)
);

CREATE TABLE Injury (
	idInjury INT NOT NULL AUTO_INCREMENT,
	injuryName varchar(200) NOT NULL,
	PRIMARY KEY (idInjury)
);

CREATE TABLE ResultingInjury (
	idResultinInjury INT NOT NULL AUTO_INCREMENT,
	idRenta INT NOT NULL,
	idInjury INT NOT NULL,
	PRIMARY KEY (idResultinInjury)
);

ALTER TABLE Client ADD CONSTRAINT Client_fk0 FOREIGN KEY (idUser) REFERENCES User(idUser);

ALTER TABLE Car ADD CONSTRAINT Car_fk0 FOREIGN KEY (idModel) REFERENCES Model(idModel);

ALTER TABLE Model ADD CONSTRAINT Model_fk0 FOREIGN KEY (idMark) REFERENCES Mark(idMark);

ALTER TABLE Model ADD CONSTRAINT Model_fk1 FOREIGN KEY (idBodyType) REFERENCES BodyType(idBodyType);

ALTER TABLE Mark ADD CONSTRAINT Mark_fk0 FOREIGN KEY (idCountry) REFERENCES Country(idCountry);

ALTER TABLE Renta ADD CONSTRAINT Renta_fk0 FOREIGN KEY (idClient) REFERENCES Client(idClient);

ALTER TABLE Renta ADD CONSTRAINT Renta_fk1 FOREIGN KEY (idCar) REFERENCES Car(idCar);

ALTER TABLE ResultingInjury ADD CONSTRAINT ResultingInjury_fk0 FOREIGN KEY (idRenta) REFERENCES Renta(idRenta);

ALTER TABLE ResultingInjury ADD CONSTRAINT ResultingInjury_fk1 FOREIGN KEY (idInjury) REFERENCES Injury(idInjury);


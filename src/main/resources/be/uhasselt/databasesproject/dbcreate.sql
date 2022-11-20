DROP TABLE IF EXISTS "loper";
CREATE TABLE IF NOT EXISTS "loper" (
	"id"	INTEGER NOT NULL UNIQUE,
	"voornaam"	TEXT NOT NULL,
	"achternaam"	TEXT NOT NULL,
	"leeftijd"	INTEGER NOT NULL,
	"gewicht"	REAL NOT NULL,
	"lengte"	REAL NOT NULL,
	"straatnaam"	TEXT NOT NULL,
	"huisnummer"	TEXT NOT NULL,
	"bus"	TEXT,
	"postcode"	TEXT NOT NULL,
	"stad"	TEXT NOT NULL,
	"land"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "vrijwilliger_wedstrijd";
CREATE TABLE IF NOT EXISTS "vrijwilliger_wedstrijd" (
	"vrijwilligerID"	INTEGER NOT NULL,
	"wedstrijdID"	INTEGER NOT NULL,
	PRIMARY KEY("vrijwilligerID","wedstrijdID"),
	FOREIGN KEY("wedstrijdID") REFERENCES "wedstrijd"("id"),
	FOREIGN KEY("vrijwilligerID") REFERENCES "vrijwilliger"("id")
);
DROP TABLE IF EXISTS "wedstrijd_klassement";
CREATE TABLE IF NOT EXISTS "wedstrijd_klassement" (
	"loperID"	INTEGER NOT NULL,
	"wedstrijdID"	INTEGER NOT NULL,
	"tijd"	REAL NOT NULL,
	PRIMARY KEY("loperID","wedstrijdID"),
	FOREIGN KEY("wedstrijdID") REFERENCES "wedstrijd"("id"),
	FOREIGN KEY("loperID") REFERENCES "loper"("id")
);
DROP TABLE IF EXISTS "algemeen_klassement";
CREATE TABLE IF NOT EXISTS "algemeen_klassement" (
	"loperID"	INTEGER NOT NULL UNIQUE,
	"prijzengeld"	INTEGER NOT NULL,
	"totaleTijd"	INTEGER NOT NULL,
	PRIMARY KEY("loperID"),
	FOREIGN KEY("loperID") REFERENCES "loper"("id")
);
DROP TABLE IF EXISTS "etappe";
CREATE TABLE IF NOT EXISTS "etappe" (
	"id"	INTEGER NOT NULL UNIQUE,
	"wedstrijdID"	INTEGER NOT NULL,
	"locatie"	TEXT NOT NULL,
	"afstand"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("wedstrijdID") REFERENCES "wedstrijd"("id")
);
DROP TABLE IF EXISTS "etappe_tijden";
CREATE TABLE IF NOT EXISTS "etappe_tijden" (
	"etappeID"	INTEGER NOT NULL,
	"loperID"	INTEGER NOT NULL,
	"tijd"	INTEGER NOT NULL,
	PRIMARY KEY("etappeID","loperID"),
	FOREIGN KEY("loperID") REFERENCES "loper"("id"),
	FOREIGN KEY("etappeID") REFERENCES "etappe"("id")
);
DROP TABLE IF EXISTS "vrijwilliger";
CREATE TABLE IF NOT EXISTS "vrijwilliger" (
	"id"	INTEGER NOT NULL UNIQUE,
	"voornaam"	TEXT NOT NULL,
	"achternaam"	TEXT NOT NULL,
	"functie"	TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "wedstrijd";
CREATE TABLE IF NOT EXISTS "wedstrijd" (
	"id"	INTEGER NOT NULL UNIQUE,
	"datum"	TEXT NOT NULL,
	"naam"	TEXT NOT NULL,
	"afstand"	INTEGER NOT NULL,
	"kostprijs"	REAL NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "loper_wedstrijd";
CREATE TABLE IF NOT EXISTS "loper_wedstrijd" (
	"loperID"	INTEGER NOT NULL,
	"wedstrijdID"	INTEGER NOT NULL,
	"rugnummer"	INTEGER NOT NULL,
	PRIMARY KEY("loperID","wedstrijdID"),
	FOREIGN KEY("loperID") REFERENCES "loper"("id"),
	FOREIGN KEY("wedstrijdID") REFERENCES "wedstrijd"("id")
);

INSERT INTO "loper" VALUES (1,'Barack','Obama',41,121.0,'2m23','Wetstraat','16',NULL,'1000','Brussel','Belgie');
INSERT INTO "loper" VALUES (2,'Linus','Sebastian',43,126.0,'1m40','Pariser Platz','1',NULL,'10117','Berlijn','Duitsland');
INSERT INTO "loper" VALUES (3,'Kris','Aerts',50,160.0,'1m60','Nazim St','29',NULL,'3500','Abbottabad','Pakistan');

INSERT INTO "vrijwilliger_wedstrijd" ("vrijwilligerID","wedstrijdID") VALUES (1,1);
INSERT INTO "vrijwilliger_wedstrijd" ("vrijwilligerID","wedstrijdID") VALUES (2,1);
INSERT INTO "vrijwilliger_wedstrijd" ("vrijwilligerID","wedstrijdID") VALUES (3,1);

INSERT INTO "wedstrijd_klassement" ("loperID","wedstrijdID","tijd") VALUES (1,1,6300.0);
INSERT INTO "wedstrijd_klassement" ("loperID","wedstrijdID","tijd") VALUES (2,1,9000.0);
INSERT INTO "wedstrijd_klassement" ("loperID","wedstrijdID","tijd") VALUES (3,1,7600.0);

INSERT INTO "algemeen_klassement" ("loperID","prijzengeld","totaleTijd") VALUES (1,20,70000);
INSERT INTO "algemeen_klassement" ("loperID","prijzengeld","totaleTijd") VALUES (2,10,60000);
INSERT INTO "algemeen_klassement" ("loperID","prijzengeld","totaleTijd") VALUES (3,5,50000);

INSERT INTO "etappe" ("id","wedstrijdID","locatie","afstand") VALUES (1,1,'bos',2.3);
INSERT INTO "etappe" ("id","wedstrijdID","locatie","afstand") VALUES (2,1,'langs het kanaal',3);
INSERT INTO "etappe" ("id","wedstrijdID","locatie","afstand") VALUES (3,1,'laatste rechte lijn',0.5);

INSERT INTO "etappe_tijden" ("etappeID","loperID","tijd") VALUES (1,1,480);
INSERT INTO "etappe_tijden" ("etappeID","loperID","tijd") VALUES (1,2,500);
INSERT INTO "etappe_tijden" ("etappeID","loperID","tijd") VALUES (1,3,600);

INSERT INTO "vrijwilliger" ("id","voornaam","achternaam","functie") VALUES (1,'Jef','Bezos','lopers in de juiste richting sturen');
INSERT INTO "vrijwilliger" ("id","voornaam","achternaam","functie") VALUES (2,'Steve','Jobs','bevoorrading');
INSERT INTO "vrijwilliger" ("id","voornaam","achternaam","functie") VALUES (3,'Bill','Gates','bevoorrading');

INSERT INTO "wedstrijd" ("id","datum","naam","afstand","kostprijs") VALUES (1,'2022-11-14','Dwars door Gent',10,5.0);
INSERT INTO "wedstrijd" ("id","datum","naam","afstand","kostprijs") VALUES (2,'2022-11-14','Dwars door Brussel',15,6.0);
INSERT INTO "wedstrijd" ("id","datum","naam","afstand","kostprijs") VALUES (3,'2022-11-14','Dwars door Leuven',5,4.0);

INSERT INTO "loper_wedstrijd" ("loperID","wedstrijdID","rugnummer") VALUES (1,1,1);
INSERT INTO "loper_wedstrijd" ("loperID","wedstrijdID","rugnummer") VALUES (2,1,2);
INSERT INTO "loper_wedstrijd" ("loperID","wedstrijdID","rugnummer") VALUES (3,1,3);
INSERT INTO "loper_wedstrijd" ("loperID","wedstrijdID","rugnummer") VALUES (1,2,1);
INSERT INTO "loper_wedstrijd" ("loperID","wedstrijdID","rugnummer") VALUES (1,3,1);

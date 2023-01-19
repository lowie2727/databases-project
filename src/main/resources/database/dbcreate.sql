DROP TABLE IF EXISTS "race";
CREATE TABLE IF NOT EXISTS "race" (
	"id"		INTEGER NOT NULL UNIQUE,
	"date"		TEXT NOT NULL,
	"name"		TEXT NOT NULL,
	"distance"	INTEGER NOT NULL CHECK ("distance" > 0),
	"price"		REAL NOT NULL CHECK ("price" > 0),
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "runner";
CREATE TABLE IF NOT EXISTS "runner" (
	"id"			INTEGER NOT NULL UNIQUE,
	"firstName"		TEXT NOT NULL,
	"familyName"	TEXT NOT NULL,
	"age"			INTEGER NOT NULL CHECK ("age" >= 12),
	"weight"		REAL NOT NULL CHECK ("weight" > 0),
	"length"		REAL NOT NULL CHECK ("weight" > 0),
	"username"	    TEXT,
    "password"	    TEXT,
	"streetName"	TEXT NOT NULL,
	"houseNumber"	TEXT NOT NULL,
	"boxNumber"		TEXT,
	"postalCode"	TEXT NOT NULL,
	"city"			TEXT NOT NULL,
	"country"		TEXT NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "segment";
CREATE TABLE IF NOT EXISTS "segment" (
	"id"		INTEGER NOT NULL UNIQUE,
	"raceID"	INTEGER NOT NULL,
	"location"	TEXT NOT NULL,
	"distance"	INTEGER NOT NULL CHECK ("distance" > 0),
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("raceID") REFERENCES "race"("id")
);
DROP TABLE IF EXISTS "volunteer";
CREATE TABLE IF NOT EXISTS "volunteer" (
	"id"			INTEGER NOT NULL UNIQUE,
	"firstName"		TEXT NOT NULL,
	"familyName"	TEXT NOT NULL,
	"username"	    TEXT,
	"password"	    TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "runner_race";
CREATE TABLE IF NOT EXISTS "runner_race" (
	"runnerID"		INTEGER NOT NULL,
	"raceID"		INTEGER NOT NULL,
	"shirtNumber"	INTEGER NOT NULL,
	"time"			INTEGER NOT NULL CHECK ("time" >= 0),
	PRIMARY KEY("runnerID","raceID"),
	FOREIGN KEY("runnerID") REFERENCES "runner"("id"),
	FOREIGN KEY("raceID") REFERENCES "race"("id")
);
DROP TABLE IF EXISTS "segment_times";
CREATE TABLE IF NOT EXISTS "segment_times" (
	"segmentID"	INTEGER NOT NULL,
	"runnerID"	INTEGER NOT NULL,
	"time"		INTEGER NOT NULL CHECK ("time" >= 0),
	PRIMARY KEY("segmentID","runnerID"),
	FOREIGN KEY("runnerID") REFERENCES "runner"("id"),
	FOREIGN KEY("segmentID") REFERENCES "segment"("id")
);
DROP TABLE IF EXISTS "volunteer_race";
CREATE TABLE IF NOT EXISTS "volunteer_race" (
	"volunteerID"	INTEGER NOT NULL,
	"raceID"		INTEGER NOT NULL,
	"job"			TEXT NOT NULL,
	PRIMARY KEY("volunteerID","raceID"),
	FOREIGN KEY("raceID") REFERENCES "race"("id"),
	FOREIGN KEY("volunteerID") REFERENCES "volunteer"("id")
);

INSERT INTO "race" ("id","date","name","distance","price") VALUES (1,'2023-11-14','Dwars door Gent',10000,5.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (2,'2023-11-14','Dwars door Brussel',15000,6.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (3,'2023-11-14','Dwars door Leuven',5000,4.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (4,'2022-11-14','Dwars door Genk',5000,50);

INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","username","streetName","houseNumber","boxNumber","postalCode","city","country")
VALUES (1,'Barack','Obama',41,121.0,2.23,'252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','BarackObama','Wetstraat','16',NULL,'1000','Brussel','Belgie');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","username","streetName","houseNumber","boxNumber","postalCode","city","country")
VALUES (2,'Linus','Sebastian',43,126.0,1.40,'252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','Linus123','Pariser Platz','1',NULL,'10117','Berlijn','Duitsland');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","username","streetName","houseNumber","boxNumber","postalCode","city","country")
VALUES (3,'Kris','Aerts',50,160.0,1.60,'252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','KingKris','Nazim St','29',NULL,'3500','Abbottabad','Pakistan');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","username","streetName","houseNumber","boxNumber","postalCode","city","country")
VALUES (4,'Joe','Biden',100,60.0,1.60,'252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','Joe1','Pennsylvania Avenue NW','1600',NULL,'DC 20500','Washington','Verenigde Staten');

INSERT INTO "segment" ("id","raceID","location","distance") VALUES (1,1,'bos',5600);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (2,1,'langs het kanaal',4200);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (3,1,'laatste rechte lijn',200);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (4,2,'stadspark',7000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (5,2,'door de heide',6000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (6,2,'brug',1000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (7,3,'campus',1000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (8,3,'oude markt',3000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (9,3,'stadspark',1000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (10,4,'genkcentrum niffo',1000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (11,4,'in de khanaal',2000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (12,4,'genk shtation',2000);

INSERT INTO "volunteer" ("id","firstName","familyName","password", "username")
VALUES (1,'Jef','Bezos',NULL,NULL);
INSERT INTO "volunteer" ("id","firstName","familyName","password", "username")
VALUES (2,'Steve','Jobs',NULL,NULL);
INSERT INTO "volunteer" ("id","firstName","familyName","password", "username")
VALUES (3,'Bill','Gates',NULL,NULL);
INSERT INTO "volunteer" ("id","firstName","familyName","password", "username")
VALUES (4,'Bob','Dylan','252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111', 'bobby1');

INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,1,1,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,1,2,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,1,3,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,1,4,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,2,1,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,2,2,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,2,3,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,2,4,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,3,1,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,3,2,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,3,3,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,3,4,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,4,1,8700);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,4,2,9800);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,4,3,6300);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,4,4,2670);

INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (1,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (1,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (1,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (1,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (2,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (2,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (2,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (2,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (3,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (3,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (3,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (3,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (4,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (4,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (4,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (4,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (5,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (5,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (5,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (5,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (6,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (6,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (6,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (6,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (7,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (7,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (7,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (7,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (8,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (8,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (8,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (8,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (9,1,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (9,2,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (9,3,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (9,4,0);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (10,1,370);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (10,2,800);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (10,3,50);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (10,4,370);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (11,1,800);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (11,2,50);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (11,3,370);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (11,4,800);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (12,1,50);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (12,2,800);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (12,3,50);
INSERT INTO "segment_times" ("segmentID","runnerID","time") VALUES (12,4,370);

INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (1,1,'bevoorrading');
INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (2,1,'richtingaangever');
INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (3,1,'richtingaangever');

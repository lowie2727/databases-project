DROP TABLE IF EXISTS "race";
CREATE TABLE IF NOT EXISTS "race" (
	"id"		INTEGER NOT NULL UNIQUE,
	"date"		TEXT NOT NULL,
	"name"		TEXT NOT NULL,
	"distance"	INTEGER NOT NULL,
	"price"		REAL NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "runner";
CREATE TABLE IF NOT EXISTS "runner" (
	"id"			INTEGER NOT NULL UNIQUE,
	"firstName"		TEXT NOT NULL,
	"familyName"	TEXT NOT NULL,
	"age"			INTEGER NOT NULL,
	"weight"		REAL NOT NULL,
	"length"		REAL NOT NULL,
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
	"distance"	INTEGER NOT NULL,
	PRIMARY KEY("id" AUTOINCREMENT),
	FOREIGN KEY("raceID") REFERENCES "race"("id")
);
DROP TABLE IF EXISTS "volunteer";
CREATE TABLE IF NOT EXISTS "volunteer" (
	"id"			INTEGER NOT NULL UNIQUE,
	"firstName"		TEXT NOT NULL,
	"familyName"	TEXT NOT NULL,
	"password"	    TEXT,
	PRIMARY KEY("id" AUTOINCREMENT)
);
DROP TABLE IF EXISTS "runner_race";
CREATE TABLE IF NOT EXISTS "runner_race" (
	"runnerID"		INTEGER NOT NULL,
	"raceID"		INTEGER NOT NULL,
	"shirtNumber"	INTEGER NOT NULL,
	"time"			INTEGER NOT NULL,
	PRIMARY KEY("runnerID","raceID"),
	FOREIGN KEY("runnerID") REFERENCES "runner"("id"),
	FOREIGN KEY("raceID") REFERENCES "race"("id")
);
DROP TABLE IF EXISTS "segment_times";
CREATE TABLE IF NOT EXISTS "segment_times" (
	"segmentID"	INTEGER NOT NULL,
	"runnerID"	INTEGER NOT NULL,
	"time"		INTEGER NOT NULL,
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
DROP TABLE IF EXISTS "global_ranking";
CREATE TABLE IF NOT EXISTS "global_ranking" (
	"runnerID"		    INTEGER NOT NULL UNIQUE,
	"prizeMoney"	    INTEGER NOT NULL,
	"averageSpeed"		REAL NOT NULL,
	PRIMARY KEY("runnerID"),
	FOREIGN KEY("runnerID") REFERENCES "runner"("id")
);

INSERT INTO "race" ("id","date","name","distance","price") VALUES (1,'2023-11-14','Dwars door Gent',10000,5.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (2,'2023-11-14','Dwars door Brussel',15000,6.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (3,'2023-11-14','Dwars door Leuven',5000,4.0);
INSERT INTO "race" ("id","date","name","distance","price") VALUES (4,'2022-11-14','Dwars door Genk',5000,50);

INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","streetName","houseNumber","boxNumber","postalCode","city","country") VALUES (1,'Barack','Obama',41,121.0,2.23,NULL,'Wetstraat','16',NULL,'1000','Brussel','Belgie');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","streetName","houseNumber","boxNumber","postalCode","city","country") VALUES (2,'Linus','Sebastian',43,126.0,1.40,NULL,'Pariser Platz','1',NULL,'10117','Berlijn','Duitsland');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","streetName","houseNumber","boxNumber","postalCode","city","country") VALUES (3,'Kris','Aerts',50,160.0,1.60,NULL,'Nazim St','29',NULL,'3500','Abbottabad','Pakistan');
INSERT INTO "runner" ("id","firstName","familyName","age","weight","length","password","streetName","houseNumber","boxNumber","postalCode","city","country") VALUES (4,'Joe','Biden',100,60.0,1.60,'252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','Pennsylvania Avenue NW','1600',null,'DC 20500','Washington','Verenigde Staten');

INSERT INTO "segment" ("id","raceID","location","distance") VALUES (1,1,'bos',2300);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (2,1,'langs het kanaal',3000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (3,1,'laatste rechte lijn',500);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (4,2,'stadspark',2300);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (5,2,'door de heide',3000);
INSERT INTO "segment" ("id","raceID","location","distance") VALUES (6,2,'brug',500);

INSERT INTO "volunteer" ("id","firstName","familyName","password") VALUES (1,'Jef','Bezos',null);
INSERT INTO "volunteer" ("id","firstName","familyName","password") VALUES (2,'Steve','Jobs',null);
INSERT INTO "volunteer" ("id","firstName","familyName","password") VALUES (3,'Bill','Gates',null);
INSERT INTO "volunteer" ("id","firstName","familyName","password") VALUES (4,'Bob','Dylan','252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111');

INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,1,1,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,1,2,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,1,3,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,1,4,0);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (1,4,1,8700);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (2,4,2,9800);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (3,4,3,6300);
INSERT INTO "runner_race" ("runnerID","raceID","shirtNumber","time") VALUES (4,4,3,2670);

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

INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (1,1,"bevoorrading");
INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (2,1,"richtingaangever");
INSERT INTO "volunteer_race" ("volunteerID","raceID","job") VALUES (3,1,"richtingaangever");

INSERT INTO "global_ranking" ("runnerID","prizeMoney","averageSpeed") VALUES (1,0,0);
INSERT INTO "global_ranking" ("runnerID","prizeMoney","averageSpeed") VALUES (2,0,0);
INSERT INTO "global_ranking" ("runnerID","prizeMoney","averageSpeed") VALUES (3,0,0);
INSERT INTO "global_ranking" ("runnerID","prizeMoney","averageSpeed") VALUES (4,0,0);

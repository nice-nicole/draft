SET MODE PostgreSQL;



CREATE TABLE IF NOT EXISTS animals (
  id int PRIMARY KEY auto_increment,
  name VARCHAR,
  endangered VARCHAR,
  condition VARCHAR,
  age TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sightings (
  id int PRIMARY KEY auto_increment,
   location VARCHAR,
   animalId INTEGER,
  rangerName VARCHAR
);


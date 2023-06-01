-- Films
CREATE TABLE IF NOT EXISTS Films(
    "id" SERIAL NOT NULL,
    "title" VARCHAR(255) NOT NULL,
    "category" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "duration" INTEGER NOT NULL
);
ALTER TABLE
    Films ADD PRIMARY KEY("id");
COMMENT
ON COLUMN
    Films."duration" IS 'Describes film''s running time in seconds';

-- Cinemas
CREATE TABLE IF NOT EXISTS "Cinemas"(
    "id" SERIAL NOT NULL,
    "is_3d" BOOLEAN NOT NULL,
    "nr_of_seats" INTEGER NOT NULL
);
ALTER TABLE
    "Cinemas" ADD PRIMARY KEY("id");

-- Provoles
CREATE TABLE IF NOT EXISTS Provoles(
    "id" SERIAL NOT NULL UNIQUE, -- Alternate key
    "film" INTEGER NOT NULL REFERENCES Films,
    "cinema" INTEGER NOT NULL REFERENCES "Cinemas",
    "start_date" TIMESTAMP NOT NULL,
    "nr_of_reservations" INTEGER NOT NULL CHECK (nr_of_reservations >= 0)
);
ALTER TABLE
    Provoles ADD PRIMARY KEY("film","cinema","start_date");

-- Users
CREATE TABLE IF NOT EXISTS Users(
    "id" SERIAL NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "username" VARCHAR(255) NOT NULL UNIQUE, -- Alternate key, no two users can have the same username
    "password" VARCHAR(255) NOT NULL,
    "type" VARCHAR(255) NOT NULL DEFAULT 'CU' CHECK (type = 'CU' OR type = 'CA' OR type = 'AD')
);
ALTER TABLE
    Users ADD PRIMARY KEY("id");
COMMENT
ON COLUMN
    Users."type" IS 'Determines the type of the user, i.e ''CU'' for customer, ''CA'' for content admin and ''AD'' for admin';

-- CustomerReservations
CREATE TABLE IF NOT EXISTS CustomerReservations(
    "customer_id" INTEGER NOT NULL REFERENCES Users("id"),
    "provoli_id" INTEGER NOT NULL REFERENCES Provoles("id"),
    "nr_of_seats" INTEGER NOT NULL CHECK (nr_of_seats > 0)
);
ALTER TABLE
    CustomerReservations ADD PRIMARY KEY("customer_id", "provoli_id");
COMMENT
ON COLUMN
    CustomerReservations."customer_id" IS 'Apply CHECK that the ID belongs to a user of type ''CU''';


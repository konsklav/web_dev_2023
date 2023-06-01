-- Films
CREATE TABLE IF NOT EXISTS Films(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "title" VARCHAR(255) NOT NULL,
    "category" VARCHAR(255) NOT NULL,
    "description" TEXT NULL,
    "duration" INTEGER NOT NULL
);

-- Cinemas
CREATE TABLE IF NOT EXISTS "Cinemas"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "is_3d" BOOLEAN NOT NULL,
    "nr_of_seats" INTEGER NOT NULL
);

-- Provoles
CREATE TABLE IF NOT EXISTS Provoles(
    "id" SERIAL NOT NULL UNIQUE, -- Alternate key
    "film" INTEGER NOT NULL REFERENCES Films,
    "cinema" INTEGER NOT NULL REFERENCES "Cinemas",
    "start_date" TIMESTAMP NOT NULL,
    "nr_of_reservations" INTEGER NOT NULL CHECK (nr_of_reservations >= 0),
    PRIMARY KEY ("film", "cinema", "start_date")
);

-- Users
CREATE TABLE IF NOT EXISTS Users(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "username" VARCHAR(255) NOT NULL UNIQUE, -- Alternate key, no two users can have the same username
    "password" VARCHAR(255) NOT NULL,
    "type" VARCHAR(255) NOT NULL DEFAULT 'CU' CHECK (type = 'CU' OR type = 'CA' OR type = 'AD')
);

-- CustomerReservations
CREATE TABLE IF NOT EXISTS CustomerReservations(
    "customer_id" INTEGER NOT NULL REFERENCES Users("id"),
    "provoli_id" INTEGER NOT NULL REFERENCES Provoles("id"),
    "nr_of_seats" INTEGER NOT NULL CHECK (nr_of_seats > 0),
    PRIMARY KEY ("customer_id", "provoli_id")
);

CREATE OR REPLACE FUNCTION check_if_customer_when_reserving()
RETURNS TRIGGER
AS
$$
BEGIN
    IF ((SELECT type FROM Users WHERE id = NEW.customer_id) <> 'CU') THEN
        RAISE EXCEPTION 'User "%" is not a customer', (SELECT name FROM Users WHERE id = NEW.customer_id);
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER validate_customer_reservation
BEFORE INSERT ON CustomerReservations
FOR EACH ROW
EXECUTE FUNCTION check_if_customer_when_reserving();



-- DISCLAIMER
-- Running this file will essentially wipe the existing database and refresh it with the sample INSERT data only.

-- "Reset" the tables by removing and creating them again IF they already exist
DROP TABLE IF EXISTS Films, Cinemas, Provoles, Users, CustomerReservations, ReservationHistory CASCADE;
-- Films
CREATE TABLE IF NOT EXISTS Films (
    "id" SERIAL NOT NULL PRIMARY KEY,
    "title" VARCHAR(128) NOT NULL,
    "category" VARCHAR(64) NOT NULL,
    "description" TEXT NULL,
    "duration" INTEGER NOT NULL
);

-- Cinemas
CREATE TABLE IF NOT EXISTS Cinemas (
    "id" SERIAL NOT NULL PRIMARY KEY,
    "is_3d" BOOLEAN NOT NULL,
    "nr_of_seats" INTEGER NOT NULL
);

-- Provoles
CREATE TABLE IF NOT EXISTS Provoles (
    "id" SERIAL NOT NULL UNIQUE, -- Alternate key
    "film" INTEGER NOT NULL REFERENCES Films ON DELETE CASCADE,
    "cinema" INTEGER NOT NULL REFERENCES Cinemas ON DELETE CASCADE,
    "start_date" TIMESTAMP NOT NULL,
    "nr_of_reservations" INTEGER NOT NULL CHECK (nr_of_reservations >= 0) DEFAULT 0,
    PRIMARY KEY ("film", "cinema", "start_date")
);

-- Users
CREATE TABLE IF NOT EXISTS Users (
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL,
    "username" VARCHAR(255) NOT NULL UNIQUE, -- Alternate key, no two users can have the same username
    "salt" VARCHAR(50) NOT NULL,
    "hash" VARCHAR(512) NOT NULL,
    "type" VARCHAR(255) NOT NULL DEFAULT 'CU' CHECK (type = 'CU' OR type = 'CA' OR type = 'AD')
);

-- CustomerReservations
CREATE TABLE IF NOT EXISTS CustomerReservations (
    "id" SERIAL NOT NULL UNIQUE, -- Alternate key
    "customer_id" INTEGER NOT NULL REFERENCES Users("id"),
    "provoli_id" INTEGER NOT NULL REFERENCES Provoles("id"),
    "nr_of_seats" INTEGER NOT NULL CHECK (nr_of_seats > 0),
    PRIMARY KEY ("customer_id", "provoli_id")
);

CREATE TABLE IF NOT EXISTS ReservationHistory (
    "customer_id" INTEGER NOT NULL REFERENCES Users("id"),
    "reservation_id" INTEGER NOT NULL REFERENCES CustomerReservations("id"),
    "reservation_time" TIMESTAMP NOT NULL,
    PRIMARY KEY ("customer_id", "reservation_id")
);

CREATE OR REPLACE FUNCTION update_provoles_reservations_and_history()
RETURNS TRIGGER
AS
$$
DECLARE
    provoli_id INT;
    nr_of_seats INT;
BEGIN
    -- Do UPDATE on Provoles
    SELECT NEW.provoli_id INTO provoli_id;
    SELECT NEW.nr_of_seats INTO nr_of_seats;

    UPDATE Provoles SET nr_of_reservations = nr_of_reservations + nr_of_seats
    WHERE Provoles.id = provoli_id;

    -- Do INSERT on ReservationHistory
    INSERT INTO ReservationHistory VALUES (NEW.customer_id, NEW.id, NOW());

    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER update_provoles_reservations_and_history
AFTER INSERT ON CustomerReservations
FOR EACH ROW
EXECUTE FUNCTION update_provoles_reservations_and_history();

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


-- INSERT data
-- CINEMAS
insert into Cinemas (is_3d, nr_of_seats) values (false, 240);
insert into Cinemas (is_3d, nr_of_seats) values (true, 243);
insert into Cinemas (is_3d, nr_of_seats) values (true, 122);
insert into Cinemas (is_3d, nr_of_seats) values (false, 88);
insert into Cinemas (is_3d, nr_of_seats) values (true, 235);
insert into Cinemas (is_3d, nr_of_seats) values (false, 130);
insert into Cinemas (is_3d, nr_of_seats) values (true, 290);
insert into Cinemas (is_3d, nr_of_seats) values (false, 204);
insert into Cinemas (is_3d, nr_of_seats) values (true, 145);
insert into Cinemas (is_3d, nr_of_seats) values (true, 72);

-- FILMS
insert into Films (title, category, description, duration) values ('Devil Doll, The', 'Horror|Sci-Fi', 'Praesent id massa id nisl venenatis lacinia. Aenean sit amet justo. Morbi ut odio.', 6813);
insert into Films (title, category, description, duration) values ('Sahara', 'Action|Drama|War', 'Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.', 10438);
insert into Films (title, category, description, duration) values ('Dark Mirror, The', 'Film-Noir|Thriller', null, 5414);
insert into Films (title, category, description, duration) values ('Hero Wanted', 'Action|Crime|Drama|Thriller', 'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', 10046);
insert into Films (title, category, description, duration) values ('Start the Revolution Without Me', 'Comedy', 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 4293);
insert into Films (title, category, description, duration) values ('Veteran, The', 'Action|Thriller', 'Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.', 8909);
insert into Films (title, category, description, duration) values ('Bad News Bears in Breaking Training, The', 'Comedy', 'In congue. Etiam justo. Etiam pretium iaculis justo.', 11294);
insert into Films (title, category, description, duration) values ('Last Season, The', 'Documentary', 'Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', 8753);
insert into Films (title, category, description, duration) values ('Who''s Your Daddy?', 'Comedy', 'Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.', 8221);
insert into Films (title, category, description, duration) values ('Diary of a Mad Housewife', 'Comedy|Drama', 'Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.', 8902);
insert into Films (title, category, description, duration) values ('Gaby: A True Story', 'Drama|Romance', 'Mauris enim leo, rhoncus sed, vestibulum sit amet, cursus id, turpis. Integer aliquet, massa id lobortis convallis, tortor risus dapibus augue, vel accumsan tellus nisi eu orci. Mauris lacinia sapien quis libero.', 10090);
insert into Films (title, category, description, duration) values ('Hole in My Heart, A (Hål i mitt hjärta, Ett)', 'Drama', 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.', 9158);
insert into Films (title, category, description, duration) values ('Missionary', 'Drama|Thriller', null, 10105);
insert into Films (title, category, description, duration) values ('Unloved, The', 'Drama', 'Nullam porttitor lacus at turpis. Donec posuere metus vitae ipsum. Aliquam non mauris.', 4468);
insert into Films (title, category, description, duration) values ('Bobo, The', 'Comedy', 'Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor. Morbi vel lectus in quam fringilla rhoncus.', 5490);
insert into Films (title, category, description, duration) values ('Ransom', 'Crime|Thriller', 'Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla. Nunc purus.', 6235);
insert into Films (title, category, description, duration) values ('Dirty Movie', 'Comedy', 'Proin eu mi. Nulla ac enim. In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem.', 8213);
insert into Films (title, category, description, duration) values ('Rose Red', 'Horror|Mystery|Thriller', 'Curabitur gravida nisi at nibh. In hac habitasse platea dictumst. Aliquam augue quam, sollicitudin vitae, consectetuer eget, rutrum at, lorem.', 7373);
insert into Films (title, category, description, duration) values ('Werner - Volles Rooäää', 'Animation|Comedy', 'Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.', 7681);
insert into Films (title, category, description, duration) values ('Secret Things (Choses secrètes)', 'Drama', 'In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus.', 11938);
insert into Films (title, category, description, duration) values ('Deadly Friend', 'Horror', 'Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.', 8559);
insert into Films (title, category, description, duration) values ('True Stories', 'Comedy|Musical', 'Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.', 7822);
insert into Films (title, category, description, duration) values ('Headless Woman, The (Mujer sin cabeza, La)', 'Drama|Mystery|Thriller', 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.', 7399);
insert into Films (title, category, description, duration) values ('Cowboy Way, The', 'Action|Comedy|Drama', 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 9309);
insert into Films (title, category, description, duration) values ('Faces of Death 3', 'Documentary|Horror', 'Nulla ut erat id mauris vulputate elementum. Nullam varius. Nulla facilisi.', 11993);
insert into Films (title, category, description, duration) values ('Delta Farce', 'Action|Adventure|Comedy', 'Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.', 6120);
insert into Films (title, category, description, duration) values ('Mabel at the Wheel', 'Comedy', 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.', 10773);
insert into Films (title, category, description, duration) values ('Paris by Night', 'Crime|Drama', 'Etiam vel augue. Vestibulum rutrum rutrum neque. Aenean auctor gravida sem.', 6785);
insert into Films (title, category, description, duration) values ('Journey for Margaret', 'Drama|War', 'Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.', 7534);
insert into Films (title, category, description, duration) values ('Jumpin'' Jack Flash', 'Action|Comedy|Romance|Thriller', 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem.', 7341);

-- Provoles
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (4, 4, '2023-07-03 17:33:24', 51);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (4, 4, '2023-07-25 10:47:02', 6);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (9, 3, '2023-07-01 17:34:11', 55);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (4, 5, '2023-06-30 14:31:18', 7);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (5, 8, '2023-07-02 00:52:08', 10);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (10, 8, '2023-07-19 07:12:22', 62);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (3, 6, '2023-05-31 21:42:24', 21);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (9, 8, '2023-06-28 21:08:59', 10);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (4, 3, '2023-05-30 01:20:38', 47);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (3, 6, '2023-06-23 17:17:35', 62);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (29, 10, '2023-06-08 01:50:46', 46);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (6, 2, '2023-06-27 10:33:32', 70);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (19, 1, '2023-05-29 19:03:03', 12);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (11, 10, '2023-07-10 02:48:19', 23);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (26, 9, '2023-07-03 09:35:33', 60);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (27, 1, '2023-07-31 12:20:30', 56);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (15, 4, '2023-05-25 09:58:00', 24);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (15, 8, '2023-06-01 19:37:25', 50);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (2, 1, '2023-07-17 19:11:04', 8);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (15, 4, '2023-06-15 18:28:44', 33);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (28, 3, '2023-06-16 19:12:32', 68);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (13, 6, '2023-06-04 13:47:45', 25);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (28, 3, '2023-07-14 12:07:06', 43);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (2, 2, '2023-07-13 00:14:59', 26);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (19, 9, '2023-07-05 19:33:27', 30);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (8, 9, '2023-06-09 10:18:17', 57);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (22, 4, '2023-06-09 20:47:32', 55);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (16, 8, '2023-07-18 22:27:08', 65);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (17, 2, '2023-06-06 16:13:44', 16);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (25, 1, '2023-07-28 03:51:19', 40);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (17, 10, '2023-07-27 06:14:57', 4);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (17, 1, '2023-06-04 04:26:13', 47);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (5, 1, '2023-07-19 02:55:58', 65);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (14, 1, '2023-06-10 01:31:57', 66);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (19, 10, '2023-06-07 14:07:54', 34);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (1, 6, '2023-07-19 06:50:00', 19);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (20, 8, '2023-07-30 08:49:55', 62);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (29, 3, '2023-07-06 20:41:01', 28);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (11, 4, '2023-07-27 03:49:44', 70);
insert into Provoles (film, cinema, start_date, nr_of_reservations) values (26, 5, '2023-06-03 22:36:50', 16);

-- Users
-- Users cannot be inserted here due salt+hash in the application

-- CustomerReservations
-- CustomerReservations cannot be inserted due to the foreign key dependency to "Users"
DROP TABLE IF EXISTS seats;

CREATE TABLE seats (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       seat_number TEXT NOT NULL UNIQUE,
                       reserved INTEGER NOT NULL DEFAULT 0
);

INSERT INTO seats (seat_number, reserved) VALUES ('A1', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A2', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A3', 1);
INSERT INTO seats (seat_number, reserved) VALUES ('A4', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A5', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A6', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A7', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A8', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A9', 0);
INSERT INTO seats (seat_number, reserved) VALUES ('A10', 0);
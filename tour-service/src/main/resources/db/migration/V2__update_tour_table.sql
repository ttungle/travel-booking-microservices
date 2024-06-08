CREATE TYPE tour_status AS ENUM('ACTIVE', 'ARCHIVE', 'CANCELED');
ALTER TABLE tour ADD COLUMN status tour_status;
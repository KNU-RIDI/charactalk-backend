ALTER TABLE member DROP COLUMN nickname;
ALTER TABLE member ADD COLUMN name VARCHAR(255) NULL;
ALTER TABLE member ADD COLUMN picture VARCHAR(255) NULL;
ALTER TABLE member ADD COLUMN role VARCHAR(255) NULL;
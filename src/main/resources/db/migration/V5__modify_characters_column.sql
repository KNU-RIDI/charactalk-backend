ALTER TABLE characters
    ADD image_url VARCHAR(255) NULL;

ALTER TABLE characters
    ADD story_id BIGINT NULL;

ALTER TABLE characters
    MODIFY story_id BIGINT NOT NULL;

ALTER TABLE characters
    ADD CONSTRAINT FK_CHARACTERS_ON_STORY FOREIGN KEY (story_id) REFERENCES story (id);
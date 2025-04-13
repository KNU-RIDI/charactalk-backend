CREATE TABLE story
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` TEXT                  NULL,
    story_type    VARCHAR(255)          NULL,
    image_url     VARCHAR(255)          NULL,
    CONSTRAINT pk_story PRIMARY KEY (id)
);

CREATE TABLE story_tag
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    story_id BIGINT                NOT NULL,
    tag_id   BIGINT                NOT NULL,
    CONSTRAINT pk_storytag PRIMARY KEY (id)
);

CREATE TABLE tag
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (id)
);

ALTER TABLE tag
    ADD CONSTRAINT uc_tag_name UNIQUE (name);

ALTER TABLE story_tag
    ADD CONSTRAINT FK_STORYTAG_ON_STORY FOREIGN KEY (story_id) REFERENCES story (id);

ALTER TABLE story_tag
    ADD CONSTRAINT FK_STORYTAG_ON_TAG FOREIGN KEY (tag_id) REFERENCES tag (id);


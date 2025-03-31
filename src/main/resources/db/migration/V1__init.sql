CREATE TABLE `character`
(
    character_id  BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    CONSTRAINT pk_character PRIMARY KEY (character_id)
);

CREATE TABLE chat
(
    chat_id      BIGINT AUTO_INCREMENT NOT NULL,
    created_at   datetime              NULL,
    message      VARCHAR(255)          NULL,
    sender_id    BIGINT                NULL,
    sender_type  VARCHAR(255)          NULL,
    chat_room_id BIGINT                NULL,
    CONSTRAINT pk_chat PRIMARY KEY (chat_id)
);

CREATE TABLE chat_room
(
    chat_room_id   BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime              NULL,
    char_room_name VARCHAR(255)          NULL,
    char_room_type VARCHAR(255)          NULL,
    member_id      BIGINT                NULL,
    character_id   BIGINT                NULL,
    CONSTRAINT pk_chatroom PRIMARY KEY (chat_room_id)
);

CREATE TABLE member
(
    member_id  BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    nickname   VARCHAR(255)          NULL,
    email      VARCHAR(255)          NULL,
    gender     VARCHAR(255)          NULL,
    birth      VARCHAR(255)          NULL,
    CONSTRAINT pk_member PRIMARY KEY (member_id)
);

ALTER TABLE chat_room
    ADD CONSTRAINT FK_CHATROOM_ON_CHARACTER FOREIGN KEY (character_id) REFERENCES `character` (character_id);

ALTER TABLE chat_room
    ADD CONSTRAINT FK_CHATROOM_ON_MEMBER FOREIGN KEY (member_id) REFERENCES member (member_id);

ALTER TABLE chat
    ADD CONSTRAINT FK_CHAT_ON_CHAT_ROOM FOREIGN KEY (chat_room_id) REFERENCES chat_room (chat_room_id);
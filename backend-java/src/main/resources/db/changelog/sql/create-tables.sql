CREATE SEQUENCE IF NOT EXISTS chat.user_seq;

CREATE TABLE IF NOT EXISTS chat.users
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('chat.user_seq'),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.rooms
(
    id   UUID PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS chat.room_members
(
    room_id UUID REFERENCES chat.rooms (id),
    user_id BIGINT REFERENCES chat.users (id),
    PRIMARY KEY (room_id, user_id)
);

CREATE TABLE IF NOT EXISTS chat.messages
(
    id      UUID PRIMARY KEY,
    content TEXT      NOT NULL,
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id BIGINT REFERENCES chat.users (id),
    room_id UUID REFERENCES chat.rooms (id)
);

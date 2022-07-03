INSERT INTO room (id, title, tone)
VALUES ('1bfff94a-b70e-4b39-bd2a-be1c0f898589', 'anyTitle', '0');

INSERT INTO location(id, title, associated_pinyin_sound)
VALUES ('1bfff94a-b70e-4b39-bd2a-be1c0f898589', 'anyTitle', 'OU');

INSERT INTO actor(id, name, associated_pinyin_sound, family, image_url)
VALUES ('1bfff94a-b70e-4b39-bd2a-be1c0f898589', 'Shakira', 'OU', '0', 'http://anyimage.com');

INSERT into movie(id, image_url, scene, actor_id, character_id, location_id, room_id)
VALUES ('1bfff94a-b70e-4b39-bd2a-be1c0f898589', 'anyImage', 'anyScene', '1bfff94a-b70e-4b39-bd2a-be1c0f898589', null, '1bfff94a-b70e-4b39-bd2a-be1c0f898589', '1bfff94a-b70e-4b39-bd2a-be1c0f898589');

INSERT into character(id, hanzi, meaning, pinyin, tone, prop)
VALUES ('1bfff94a-b70e-4b39-bd2a-be1c0f898589', 'Shi', 'anyMeaning', 'OU', '0', 'true');

UPDATE movie
SET character_id = '1bfff94a-b70e-4b39-bd2a-be1c0f898589'
WHERE character_id is null;
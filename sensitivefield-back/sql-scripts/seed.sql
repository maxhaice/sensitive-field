/* Adding roles of users */
INSERT INTO `sensitive-field`.`role` (`id`, `name`)
VALUES ('1', 'ROLE_USER');

INSERT INTO `sensitive-field`.`role` (`id`, `name`)
VALUES ('2', 'ROLE_EXTENDEDUSER');

INSERT INTO `sensitive-field`.`role` (`id`, `name`)
VALUES ('3', 'ROLE_ADMIN');
INSERT INTO `sensitive-field`.`role` (`id`, `name`)
VALUES ('4', 'ROLE_SUPERUSER');

/* Add default user with login = root, password = root */
INSERT INTO `sensitive-field`.`user` (`id`, `login`, `name`, `password`)
VALUES ('1', 'root', 'root', '$2a$10$gn2o9A6yDmdzMi/BQpw2YuzdQ4t.bpq6rQVRLDt.Ar/VBNoFk909S');

/* Adding types of events */
INSERT INTO `sensitive-field`.`event_type` (`id`, `name`)
VALUES ('1', 'vehicle');
INSERT INTO `sensitive-field`.`event_type` (`id`, `name`)
VALUES ('2', 'shoot');
INSERT INTO `sensitive-field`.`event_type` (`id`, `name`)
VALUES ('3', 't34');
INSERT INTO `sensitive-field`.`event_type` (`id`, `name`)
VALUES ('4', 'dog');

/* Adding default sensor */
INSERT INTO `sensitive-field`.`audio_sensor` (`id`, `latitude`, `longitude`, `name`)
VALUES ('1', '50.439002', '30.55552', 'default sensor');

/* Adding kinds of events */
INSERT INTO `sensitive-field`.`event_kind` (`id`, `name`, `priority`, `type_event_id`)
VALUES ('1', 'ak47', 'Warn', '4');
INSERT INTO `sensitive-field`.`event_kind` (`id`, `name`, `priority`, `type_event_id`)
VALUES ('2', 'ak34', 'SuperDangerous', '4');
INSERT INTO `sensitive-field`.`event_kind` (`id`, `name`, `priority`, `type_event_id`)
VALUES ('3', 'BMP', 'Dangerous', '3');
INSERT INTO `sensitive-field`.`event_kind` (`id`, `name`, `priority`, `type_event_id`)
VALUES ('4', 'awp', 'Default', '1');


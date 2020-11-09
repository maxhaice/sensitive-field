
CREATE TABLE `audio_sensor` (
                                `id` int NOT NULL,
                                `latitude` double DEFAULT NULL,
                                `longitude` double DEFAULT NULL,
                                `name` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `event_kind` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) DEFAULT NULL,
                              `priority` varchar(255) DEFAULT NULL,
                              `type_event_id` int DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FKk85lsgswour5fcio0l005wqwt` (`type_event_id`),
                              CONSTRAINT `FKk85lsgswour5fcio0l005wqwt` FOREIGN KEY (`type_event_id`) REFERENCES `event_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `event_type` (
                              `id` int NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `audio_event` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `date_origin` datetime(6) DEFAULT NULL,
                               `date_of_come` datetime(6) DEFAULT NULL,
                               `is_deleted` bit(1) DEFAULT NULL,
                               `latitude` double DEFAULT NULL,
                               `longitude` double DEFAULT NULL,
                               `source_persistence1` double DEFAULT NULL,
                               `source_persistence2` double DEFAULT NULL,
                               `source_persistence3` double DEFAULT NULL,
                               `source_type1` varchar(255) DEFAULT NULL,
                               `source_type2` varchar(255) DEFAULT NULL,
                               `source_type3` varchar(255) DEFAULT NULL,
                               `audio_sensor_id` int NOT NULL,
                               `event_kind_id` int NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKqb1g553i9iog97ib73gwfnq6m` (`audio_sensor_id`),
                               KEY `FKpieflb9ee41k402y8i4kwkanh` (`event_kind_id`),
                               CONSTRAINT `FKpieflb9ee41k402y8i4kwkanh` FOREIGN KEY (`event_kind_id`) REFERENCES `event_kind` (`id`),
                               CONSTRAINT `FKqb1g553i9iog97ib73gwfnq6m` FOREIGN KEY (`audio_sensor_id`) REFERENCES `audio_sensor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `login` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKew1hvam8uwaknuaellwhqchhb` (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_roles` (
                              `user_id` int NOT NULL,
                              `role_id` bigint NOT NULL,
                              PRIMARY KEY (`user_id`,`role_id`),
                              KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
                              CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                              CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(60) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK_epk9im9l9q67xmwi4hbed25do` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

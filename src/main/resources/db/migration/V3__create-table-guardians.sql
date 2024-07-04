CREATE TABLE guardians
(
    id    BIGINT AUTO_INCREMENT NOT NULL,
    name  VARCHAR(255)          NULL,
    phone VARCHAR(255)          NULL,
    email VARCHAR(255)          NULL,
    CONSTRAINT pk_guardians PRIMARY KEY (id)
);
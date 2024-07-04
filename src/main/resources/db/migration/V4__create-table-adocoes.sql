CREATE TABLE adoptions
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    date                 datetime              NULL,
    guardian_id          BIGINT                NULL,
    pet_id               BIGINT                NULL,
    reason               VARCHAR(255)          NULL,
    status               VARCHAR(255)          NULL,
    status_justification VARCHAR(255)          NULL,
    CONSTRAINT pk_adoptions PRIMARY KEY (id)
);

ALTER TABLE adoptions
    ADD CONSTRAINT FK_ADOPTIONS_ON_GUARDIAN FOREIGN KEY (guardian_id) REFERENCES guardians (id);

ALTER TABLE adoptions
    ADD CONSTRAINT FK_ADOPTIONS_ON_PET FOREIGN KEY (pet_id) REFERENCES pets (id);

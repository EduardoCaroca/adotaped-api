CREATE TABLE pets
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    type       VARCHAR(255)          NULL,
    name       VARCHAR(255)          NULL,
    breed      VARCHAR(255)          NULL,
    age        INT                   NULL,
    color      VARCHAR(255)          NULL,
    weight     FLOAT                 NULL,
    adopted    BIT(1)                NULL,
    shelter_id BIGINT                NULL,
    CONSTRAINT pk_pets PRIMARY KEY (id)
);

ALTER TABLE pets
    ADD CONSTRAINT FK_PETS_ON_SHELTER FOREIGN KEY (shelter_id) REFERENCES shelters (id);
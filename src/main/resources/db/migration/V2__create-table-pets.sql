CREATE TABLE pets (
      id BIGINT NOT NULL AUTO_INCREMENT,
      type VARCHAR(100) NOT NULL,
      name VARCHAR(100) NOT NULL,
      breed VARCHAR(100) NOT NULL,
      age INT NOT NULL,
      color VARCHAR(100) NOT NULL,
      weight DECIMAL(4,2) NOT NULL,
      adopted BOOLEAN NOT NULL,
      shelter_id BIGINT NULL,
      CONSTRAINT pk_pets PRIMARY KEY (id)
);

ALTER TABLE pets
    ADD CONSTRAINT FK_PETS_ON_SHELTER FOREIGN KEY (shelter_id) REFERENCES shelters (id);
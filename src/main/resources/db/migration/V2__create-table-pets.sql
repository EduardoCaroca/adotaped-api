CREATE TABLE pets (
      id BIGINT NOT NULL AUTO_INCREMENT,
      type VARCHAR(100) NOT NULL,
      name VARCHAR(100) NOT NULL,
      breed VARCHAR(100) NOT NULL,
      age INT NOT NULL,
      color VARCHAR(100) NOT NULL,
      weight DECIMAL(4,2) NOT NULL,
      shelter_id BIGINT NOT NULL,
      adopted BOOLEAN NOT NULL,
      PRIMARY KEY (id),
      CONSTRAINT fk_pets_shelter_id FOREIGN KEY (shelter_id) REFERENCES shelters(id)
);
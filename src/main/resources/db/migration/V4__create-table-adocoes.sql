CREATE TABLE adoptions (
   id BIGINT NOT NULL AUTO_INCREMENT,
   date DATETIME NOT NULL,
   guardian_id BIGINT NOT NULL,
   pet_id BIGINT NOT NULL,
   reason VARCHAR(255) NOT NULL,
   status VARCHAR(100) NOT NULL,
   status_justification VARCHAR(255),
   PRIMARY KEY (id),
   CONSTRAINT fk_adoptions_guardian_id FOREIGN KEY (guardian_id) REFERENCES guardians(id),
   CONSTRAINT fk_adoptions_pet_id FOREIGN KEY (pet_id) REFERENCES pets(id)
);

ALTER TABLE adoptions
    ADD CONSTRAINT uc_adoptions_pet UNIQUE (pet_id);

ALTER TABLE adoptions
    ADD CONSTRAINT FK_adoptions_ON_GUARDIAN FOREIGN KEY (guardian_id) REFERENCES guardians (id);

ALTER TABLE adoptions
    ADD CONSTRAINT FK_adoptions_ON_PET FOREIGN KEY (pet_id) REFERENCES pets (id);

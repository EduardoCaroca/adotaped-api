package adotapet.api.shelter;


import adotapet.api.pet.Pet;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelters")
public class ShelterController {

    @Autowired
    private ShelterRepository repository;

    @GetMapping
    public ResponseEntity<List<Shelter>> list() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid Shelter shelter) {
        boolean nameAlreadyRegistered = repository.existsByName(shelter.getName());
        boolean phoneAlreadyRegistered = repository.existsByPhone(shelter.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(shelter.getEmail());

        if (nameAlreadyRegistered || phoneAlreadyRegistered || emailAlreadyRegistered) {
            return ResponseEntity.badRequest().body("Data already registered for another shelter!");
        } else {
            repository.save(shelter);
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/{idOrName}/pets")
    public ResponseEntity<List<Pet>> listPets(@PathVariable String idOrName) {
        try {
            Long id = Long.parseLong(idOrName);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            return ResponseEntity.ok(pets);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = repository.findByName(idOrName).getPets();
                return ResponseEntity.ok(pets);
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @PostMapping("/{idOrName}/pets")
    @Transactional
    public ResponseEntity<String> registerPet(@PathVariable String idOrName, @RequestBody @Valid Pet pet) {
        try {
            Long id = Long.parseLong(idOrName);
            Shelter shelter = repository.getReferenceById(id);
            pet.setShelter(shelter);
            pet.setAdopted(false);
            shelter.getPets().add(pet);
            repository.save(shelter);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException nfe) {
            try {
                Shelter shelter = repository.findByName(idOrName);
                pet.setShelter(shelter);
                pet.setAdopted(false);
                shelter.getPets().add(pet);
                repository.save(shelter);
                return ResponseEntity.ok().build();
            } catch (EntityNotFoundException enfe) {
                return ResponseEntity.notFound().build();
            }
        }
    }

}

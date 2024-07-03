package adotapet.api.shelter;


import adotapet.api.pet.Pet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelters")
@RequiredArgsConstructor
public class ShelterController {

    private final ShelterService service;

    @GetMapping
    public ResponseEntity<List<Shelter>> list() {
        List<Shelter> shelters = service.listAll();
        return ResponseEntity.ok(shelters);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid Shelter shelter) {
        service.register(shelter);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idOrName}/pets")
    public ResponseEntity<List<Pet>> listPets(@PathVariable String idOrName) {
        return ResponseEntity.ok(service.listAllPets(idOrName));
    }

    @PostMapping("/{idOrName}/pets")
    @Transactional
    public ResponseEntity<String> registerPet(@PathVariable String idOrName, @RequestBody @Valid Pet pet) {
        service.registerPet(idOrName, pet);
        return ResponseEntity.ok().build();
    }

}

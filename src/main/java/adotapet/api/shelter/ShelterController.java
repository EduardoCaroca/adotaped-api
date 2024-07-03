package adotapet.api.shelter;


import adotapet.api.pet.payload.PetDTO;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.payload.ShelterDTO;
import adotapet.api.shelter.payload.ShelterForm;
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
    public ResponseEntity<List<ShelterDTO>> list() {
        List<ShelterDTO> shelters = service.listAll();
        return ResponseEntity.ok(shelters);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid ShelterForm form) {
        service.register(form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idOrName}/pets")
    public ResponseEntity<List<PetDTO>> listPets(@PathVariable String idOrName) {
        return ResponseEntity.ok(service.listAllPets(idOrName));
    }

    @PostMapping("/{idOrName}/pets")
    @Transactional
    public ResponseEntity<String> registerPet(@PathVariable String idOrName, @RequestBody @Valid PetForm pet) {
        service.registerPet(idOrName, pet);
        return ResponseEntity.ok().build();
    }

}

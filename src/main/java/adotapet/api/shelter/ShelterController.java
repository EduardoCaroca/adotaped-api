package adotapet.api.shelter;


import adotapet.api.pet.payload.PetDTO;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.payload.ShelterDTO;
import adotapet.api.shelter.payload.ShelterForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<ShelterDTO> register(@RequestBody @Valid ShelterForm form) {
        ShelterDTO shelter = service.register(form);
        return ResponseEntity.created(URI.create("/shelters/" + shelter.getId())).body(shelter);
    }

    @GetMapping("/{id}/pets")
    public ResponseEntity<List<PetDTO>> listPets(@PathVariable Long id) {
        return ResponseEntity.ok(service.listAllPets(id));
    }

    @PostMapping("/{id}/pets")
    public ResponseEntity<PetDTO> registerPet(@PathVariable Long id, @RequestBody @Valid PetForm form) {
        PetDTO pet = service.registerPet(id, form);
        return ResponseEntity.created(URI.create("/pets/" + pet.getId())).body(pet);
    }

}

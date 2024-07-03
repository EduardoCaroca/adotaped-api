package adotapet.api.pet;

import adotapet.api.pet.payload.PetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService service;

    @GetMapping
    public ResponseEntity<List<PetDTO>> listAllAvailable() {
        List<PetDTO> available = service.listAllAvailable();
        return ResponseEntity.ok(available);
    }

}

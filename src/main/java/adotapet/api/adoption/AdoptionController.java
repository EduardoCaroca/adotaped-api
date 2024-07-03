package adotapet.api.adoption;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService service;


    @PostMapping
    @Transactional
    public ResponseEntity<String> request(@RequestBody @Valid Adoption adoption) {
        service.request(adoption);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve")
    @Transactional
    public ResponseEntity<String> approve(@RequestBody @Valid Adoption adoption) {
        service.approve(adoption);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject")
    @Transactional
    public ResponseEntity<String> reject(@RequestBody @Valid Adoption adoption) {
        service.reject(adoption);
        return ResponseEntity.ok().build();
    }

}

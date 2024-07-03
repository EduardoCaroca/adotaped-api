package adotapet.api.adoption;

import adotapet.api.adoption.payload.AdoptionForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService service;


    @PostMapping
    public ResponseEntity<String> request(@RequestBody @Valid AdoptionForm form) {
        service.request(form);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        service.approve(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        service.reject(id);
        return ResponseEntity.ok().build();
    }

}

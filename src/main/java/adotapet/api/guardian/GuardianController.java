package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService service;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid GuardianForm form) {
        service.register(form);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid GuardianForm form) {
        service.update(id, form);
        return ResponseEntity.ok().build();
    }

}

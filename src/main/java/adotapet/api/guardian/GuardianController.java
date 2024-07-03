package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianDTO;
import adotapet.api.guardian.payload.GuardianForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService service;

    @PostMapping
    public ResponseEntity<GuardianDTO> register(@RequestBody @Valid GuardianForm form) {
        GuardianDTO guardianDTO = service.register(form);
        return ResponseEntity.ok(guardianDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuardianDTO> update(@PathVariable Long id, @RequestBody @Valid GuardianForm form) {
        GuardianDTO guardianDTO = service.update(id, form);
        return ResponseEntity.ok(guardianDTO);
    }

    @GetMapping
    public ResponseEntity<List<GuardianDTO>> list() {
        List<GuardianDTO> guardians = service.listAll();
        return ResponseEntity.ok(guardians);
    }

}

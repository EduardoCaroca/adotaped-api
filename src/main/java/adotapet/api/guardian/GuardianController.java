package adotapet.api.guardian;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardians")
@RequiredArgsConstructor
public class GuardianController {

    private final GuardianService service;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid Guardian guardian) {
        service.register(guardian);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> update(@RequestBody @Valid Guardian guardian) {
        service.update(guardian);
        return ResponseEntity.ok().build();
    }

}

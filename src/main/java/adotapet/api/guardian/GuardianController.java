package adotapet.api.guardian;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guardians")
public class GuardianController {

    @Autowired
    private GuardianRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> register(@RequestBody @Valid Guardian guardian) {
        boolean phoneAlreadyRegistered = repository.existsByPhone(guardian.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(guardian.getEmail());

        if (phoneAlreadyRegistered || emailAlreadyRegistered) {
            return ResponseEntity.badRequest().body("Data already registered for another guardian!");
        } else {
            repository.save(guardian);
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> update(@RequestBody @Valid Guardian guardian) {
        repository.save(guardian);
        return ResponseEntity.ok().build();
    }

}

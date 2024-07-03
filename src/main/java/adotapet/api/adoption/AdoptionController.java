package adotapet.api.adoption;

import adotapet.api.model.enums.AdoptionStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionRepository repository;

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping
    @Transactional
    public ResponseEntity<String> request(@RequestBody @Valid Adoption adoption) {
        if (adoption.getPet().getAdopted() == true) {
            return ResponseEntity.badRequest().body("Pet has already been adopted!");
        } else {
            List<Adoption> adoptions = repository.findAll();
            for (Adoption a : adoptions) {
                if (a.getGuardian() == adoption.getGuardian() && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    return ResponseEntity.badRequest().body("Guardian already has another adoption pending evaluation!");
                }
            }
            for (Adoption a : adoptions) {
                if (a.getPet() == adoption.getPet() && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    return ResponseEntity.badRequest().body("Pet is already awaiting evaluation for adoption!");
                }
            }
            for (Adoption a : adoptions) {
                int counter = 0;
                if (a.getGuardian() == adoption.getGuardian() && a.getStatus() == AdoptionStatus.APPROVED) {
                    counter = counter + 1;
                }
                if (counter == 5) {
                    return ResponseEntity.badRequest().body("Guardian has reached the maximum limit of 5 adoptions!");
                }
            }
        }
        adoption.setDate(LocalDateTime.now());
        adoption.setStatus(AdoptionStatus.PENDING_EVALUATION);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getPet().getShelter().getEmail());
        email.setSubject("Adoption request");
        email.setText("Hello " + adoption.getPet().getShelter().getName() + "!\n\nAn adoption request was registered today for the pet: " + adoption.getPet().getName() + ". \nPlease evaluate for approval or rejection.");
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/approve")
    @Transactional
    public ResponseEntity<String> approve(@RequestBody @Valid Adoption adoption) {
        adoption.setStatus(AdoptionStatus.APPROVED);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption approved");
        email.setText("Congratulations " + adoption.getGuardian().getName() + "!\n\nYour adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been approved.\nPlease contact the shelter " + adoption.getPet().getShelter().getName() + " to schedule the pickup of your pet.");
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject")
    @Transactional
    public ResponseEntity<String> reject(@RequestBody @Valid Adoption adoption) {
        adoption.setStatus(AdoptionStatus.REJECTED);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption rejected");
        email.setText("Hello " + adoption.getGuardian().getName() + "!\n\nUnfortunately, your adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been rejected by the shelter " + adoption.getPet().getShelter().getName() + " with the following justification: " + adoption.getStatusJustification());
        emailSender.send(email);

        return ResponseEntity.ok().build();
    }

}

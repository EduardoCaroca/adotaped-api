package adotapet.api.adoption;

import adotapet.api.model.enums.AdoptionStatus;
import adotapet.api.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private final JavaMailSender emailSender;

    private final AdoptionRepository repository;

    @Override
    public void request(Adoption adoption) {
        if (adoption.getPet().getAdopted()) {
            throw new BadRequestException("Pet has already been adopted!");
        } else {
            List<Adoption> adoptions = repository.findAll();
            for (Adoption a : adoptions) {
                if (a.getGuardian() == adoption.getGuardian() && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    throw new BadRequestException("Guardian already has another adoption pending evaluation!");
                }
            }
            for (Adoption a : adoptions) {
                if (a.getPet() == adoption.getPet() && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    throw new BadRequestException("Pet is already awaiting evaluation for adoption!");
                }
            }
            for (Adoption a : adoptions) {
                int counter = 0;
                if (a.getGuardian() == adoption.getGuardian() && a.getStatus() == AdoptionStatus.APPROVED) {
                    counter = counter + 1;
                }
                if (counter == 5) {
                    throw new BadRequestException("Guardian has reached the maximum limit of 5 adoptions!");
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
    }

    @Override
    public void approve(Adoption adoption) {
        adoption.setStatus(AdoptionStatus.APPROVED);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption approved");
        email.setText("Congratulations " + adoption.getGuardian().getName() + "!\n\nYour adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been approved.\nPlease contact the shelter " + adoption.getPet().getShelter().getName() + " to schedule the pickup of your pet.");
        emailSender.send(email);
    }

    @Override
    public void reject(Adoption adoption) {
        adoption.setStatus(AdoptionStatus.REJECTED);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption rejected");
        email.setText("Hello " + adoption.getGuardian().getName() + "!\n\nUnfortunately, your adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been rejected by the shelter " + adoption.getPet().getShelter().getName() + " with the following justification: " + adoption.getStatusJustification());
        emailSender.send(email);
    }
}

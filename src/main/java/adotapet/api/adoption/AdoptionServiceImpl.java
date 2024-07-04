package adotapet.api.adoption;

import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.adoption.validations.Validations;
import adotapet.api.commom.domain.config.email.EmailService;
import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.guardian.Guardian;
import adotapet.api.guardian.GuardianRepository;
import adotapet.api.pet.Pet;
import adotapet.api.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository repository;
    private final GuardianRepository guardianRepository;
    private final PetRepository petRepository;
    private final EmailService emailService;
    private final List<Validations> validations;

    @Override
    @Transactional
    public void request(AdoptionForm form) {
        validations.forEach(validation -> validation.validate(form));

        Pet pet = petRepository.findById(form.getPetId()).orElseThrow(() -> new BadRequestException("Pet not found!"));
        Guardian guardian = guardianRepository.findById(form.getGuardianId()).orElseThrow(() -> new BadRequestException("Guardian not found!"));
        Adoption adoption = new Adoption(form);
        adoption.update(guardian);
        adoption.update(pet);
        repository.save(adoption);

        sendEmailForRequest(adoption);
    }

    @Override
    @Transactional
    public void approve(Long id) {
        Adoption adoption = repository.findById(id).orElseThrow(() -> new BadRequestException("Adoption not found!"));
        adoption.approve();
        repository.save(adoption);

        sendEmailForApprove(adoption);
    }

    @Override
    @Transactional
    public void reject(Long id) {
        Adoption adoption = repository.findById(id).orElseThrow(() -> new BadRequestException("Adoption not found!"));
        adoption.reject();
        repository.save(adoption);

        sendEmailForReject(adoption);
    }

    private void sendEmailForRequest(Adoption adoption) {
        String to = adoption.getPet().getShelter().getEmail();
        String subject = "New adoption request";
        String text = "Hello " + adoption.getPet().getShelter().getName() + "!\n\nA new adoption request has been made for the pet " + adoption.getPet().getName() + " by the guardian " + adoption.getGuardian().getName() + ".\nPlease access the system to evaluate the request.";
        emailService.sendEmail(to, subject, text);
    }

    private void sendEmailForApprove(Adoption adoption) {
        String to = adoption.getGuardian().getEmail();
        String subject = "Adoption approved";
        String text = "Congratulations " + adoption.getGuardian().getName() + "!\n\nYour adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been approved.\nPlease contact the shelter " + adoption.getPet().getShelter().getName() + " to schedule the pickup of your pet.";
        emailService.sendEmail(to, subject, text);
    }

    private void sendEmailForReject(Adoption adoption) {
        String to = adoption.getGuardian().getEmail();
        String subject = "Adoption rejected";
        String text = "Hello " + adoption.getGuardian().getName() + "!\n\nUnfortunately, your adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been rejected by the shelter " + adoption.getPet().getShelter().getName() + " with the following justification: " + adoption.getStatusJustification();
        emailService.sendEmail(to, subject, text);
    }
}

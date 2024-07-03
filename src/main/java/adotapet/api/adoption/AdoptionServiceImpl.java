package adotapet.api.adoption;

import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.guardian.Guardian;
import adotapet.api.guardian.GuardianRepository;
import adotapet.api.model.enums.AdoptionStatus;
import adotapet.api.model.exceptions.BadRequestException;
import adotapet.api.pet.Pet;
import adotapet.api.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

    private final JavaMailSender emailSender;
    private final AdoptionRepository repository;
    private final GuardianRepository guardianRepository;
    private final PetRepository petRepository;

    @Override
    @Transactional
    public void request(AdoptionForm form) {
        Guardian guardian = guardianRepository.findById(form.getGuardianId()).orElseThrow(() -> new BadRequestException("Guardian not found!"));
        Pet pet = petRepository.findById(form.getPetId()).orElseThrow(() -> new BadRequestException("Pet not found!"));

        if (pet.getAdopted()) {
            throw new BadRequestException("Pet has already been adopted!");
        } else {
            List<Adoption> adoptions = repository.findAll();
            for (Adoption a : adoptions) {
                if (a.getGuardian() == guardian && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    throw new BadRequestException("Guardian already has another adoption pending evaluation!");
                }
            }
            for (Adoption a : adoptions) {
                if (a.getPet() == pet && a.getStatus() == AdoptionStatus.PENDING_EVALUATION) {
                    throw new BadRequestException("Pet is already awaiting evaluation for adoption!");
                }
            }
            for (Adoption a : adoptions) {
                int counter = 0;
                if (a.getGuardian() == guardian && a.getStatus() == AdoptionStatus.APPROVED) {
                    counter = counter + 1;
                }
                if (counter == 5) {
                    throw new BadRequestException("Guardian has reached the maximum limit of 5 adoptions!");
                }
            }
        }
        Adoption adoption = new Adoption(form);
        adoption.update(guardian);
        adoption.update(pet);
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getPet().getShelter().getEmail());
        email.setSubject("Adoption request");
        email.setText("Hello " + adoption.getPet().getShelter().getName() + "!\n\nAn adoption request was registered today for the pet: " + adoption.getPet().getName() + ". \nPlease evaluate for approval or rejection.");
        emailSender.send(email);
    }

    @Override
    @Transactional
    public void approve(Long id) {
        Adoption adoption = repository.findById(id).orElseThrow(() -> new BadRequestException("Adoption not found!"));
        adoption.approve();
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption approved");
        email.setText("Congratulations " + adoption.getGuardian().getName() + "!\n\nYour adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been approved.\nPlease contact the shelter " + adoption.getPet().getShelter().getName() + " to schedule the pickup of your pet.");
        emailSender.send(email);
    }

    @Override
    @Transactional
    public void reject(Long id) {
        Adoption adoption = repository.findById(id).orElseThrow(() -> new BadRequestException("Adoption not found!"));
        adoption.reject();
        repository.save(adoption);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("adopet@email.com");
        email.setTo(adoption.getGuardian().getEmail());
        email.setSubject("Adoption rejected");
        email.setText("Hello " + adoption.getGuardian().getName() + "!\n\nUnfortunately, your adoption of the pet " + adoption.getPet().getName() + ", requested on " + adoption.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", has been rejected by the shelter " + adoption.getPet().getShelter().getName() + " with the following justification: " + adoption.getStatusJustification());
        emailSender.send(email);
    }
}

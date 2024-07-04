package adotapet.api.adoption.validations;

import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationPetAdopted implements Validations {

    private final PetRepository petRepository;

    public void validate(AdoptionForm form) {
        boolean exists = petRepository.existsByIdAndAdoptedTrue(form.getPetId());
        if (exists) {
            throw new BadRequestException("Pet has already been adopted!");
        }
    }
}

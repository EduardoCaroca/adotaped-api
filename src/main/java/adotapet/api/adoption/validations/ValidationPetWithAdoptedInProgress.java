package adotapet.api.adoption.validations;

import adotapet.api.adoption.AdoptionRepository;
import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.commom.domain.model.enums.AdoptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationPetWithAdoptedInProgress implements Validations {

    private final AdoptionRepository adoptionRepository;

    public void validate(AdoptionForm form) {
        boolean exist = adoptionRepository.existsAllByPet_IdAndStatus(form.getPetId(), AdoptionStatus.PENDING_EVALUATION);
        if (exist) {
            throw new BadRequestException("Pet is already awaiting evaluation for adoption!");
        }
    }
}

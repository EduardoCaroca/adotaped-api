package adotapet.api.adoption.validations;

import adotapet.api.adoption.AdoptionRepository;
import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.commom.domain.model.enums.AdoptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationGuardianWithAdoptionLimit implements Validations {

    private final AdoptionRepository adoptionRepository;

    public void validate(AdoptionForm form) {
        int counter = adoptionRepository.countAllByGuardian_IdAndStatus(form.getGuardianId(), AdoptionStatus.APPROVED);
        if (counter == 5) {
            throw new BadRequestException("Guardian has reached the maximum limit of 5 adoptions!");
        }
    }
}

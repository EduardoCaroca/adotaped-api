package adotapet.api.adoption.validations;

import adotapet.api.adoption.AdoptionRepository;
import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.commom.domain.model.enums.AdoptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationGuardianWithAdoptedInProgress implements Validations {

    private final AdoptionRepository adoptionRepository;

    public void validate(AdoptionForm form) {
        boolean exist = adoptionRepository.existsAllByGuardian_IdAndStatus(form.getGuardianId(), AdoptionStatus.PENDING_EVALUATION);
        if (exist) {
            throw new BadRequestException("Guardian already has another adoption pending evaluation!");
        }
    }
}

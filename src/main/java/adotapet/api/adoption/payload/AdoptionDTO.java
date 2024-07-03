package adotapet.api.adoption.payload;

import adotapet.api.guardian.payload.GuardianDTO;
import adotapet.api.model.enums.AdoptionStatus;
import adotapet.api.pet.payload.PetDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdoptionDTO {
    private Long id;
    private LocalDateTime date;
    private String reason;
    private AdoptionStatus status;
    private String statusJustification;
    private GuardianDTO guardian;
    private PetDTO pet;
}

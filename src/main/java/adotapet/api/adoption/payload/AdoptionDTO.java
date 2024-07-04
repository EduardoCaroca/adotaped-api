package adotapet.api.adoption.payload;

import adotapet.api.commom.domain.model.enums.AdoptionStatus;
import adotapet.api.guardian.payload.GuardianDTO;
import adotapet.api.pet.payload.PetDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdoptionDTO {
    private Long id;
    private LocalDateTime date;
    private String reason;
    private AdoptionStatus status;
    private String statusJustification;
    private GuardianDTO guardian;
    private PetDTO pet;
}

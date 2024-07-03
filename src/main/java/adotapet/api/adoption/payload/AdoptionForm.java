package adotapet.api.adoption.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdoptionForm {
    @NotBlank
    private Long guardianId;
    @NotBlank
    private Long petId;
    @NotBlank
    private String reason;
}

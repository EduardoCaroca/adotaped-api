package adotapet.api.adoption.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdoptionForm {
    @NotNull
    private Long guardianId;
    @NotNull
    private Long petId;
    @NotBlank
    private String reason;
}

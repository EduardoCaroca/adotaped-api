package adotapet.api.pet.payload;

import adotapet.api.commom.domain.model.enums.PetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PetForm {
    @NotNull
    private PetType type;

    @NotBlank
    private String name;

    @NotBlank
    private String breed;

    @NotNull
    private Integer age;

    @NotBlank
    private String color;

    @NotNull
    private Float weight;
}

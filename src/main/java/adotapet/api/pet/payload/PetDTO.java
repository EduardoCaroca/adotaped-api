package adotapet.api.pet.payload;

import adotapet.api.adoption.payload.AdoptionDTO;
import adotapet.api.commom.domain.model.enums.PetType;
import adotapet.api.shelter.payload.ShelterDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDTO {
    private Long id;
    private PetType type;
    private String name;
    private String breed;
    private Integer age;
    private String color;
    private Float weight;
    private Boolean adopted;
    private ShelterDTO shelter;
    private AdoptionDTO adoption;
}

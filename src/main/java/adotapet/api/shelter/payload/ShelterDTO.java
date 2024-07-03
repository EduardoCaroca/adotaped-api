package adotapet.api.shelter.payload;

import adotapet.api.pet.payload.PetDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShelterDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private List<PetDTO> pets;
}

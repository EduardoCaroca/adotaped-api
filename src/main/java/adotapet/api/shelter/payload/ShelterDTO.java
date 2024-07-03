package adotapet.api.shelter.payload;

import adotapet.api.pet.payload.PetDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShelterDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private List<PetDTO> pets;
}

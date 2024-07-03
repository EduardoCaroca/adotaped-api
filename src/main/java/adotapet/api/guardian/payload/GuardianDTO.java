package adotapet.api.guardian.payload;

import adotapet.api.adoption.payload.AdoptionDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GuardianDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private List<AdoptionDTO> adoptions;
}

package adotapet.api.shelter;

import adotapet.api.pet.payload.PetDTO;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.payload.ShelterDTO;
import adotapet.api.shelter.payload.ShelterForm;

import java.util.List;

public interface ShelterService {

    List<ShelterDTO> listAll();

    ShelterDTO register(ShelterForm form);

    List<PetDTO> listAllPets(Long id);

    PetDTO registerPet(Long id, PetForm form);
}

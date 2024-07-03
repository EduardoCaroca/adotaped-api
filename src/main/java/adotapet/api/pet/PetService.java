package adotapet.api.pet;

import adotapet.api.pet.payload.PetDTO;

import java.util.List;

public interface PetService {

    List<PetDTO> listAllAvailable();

}

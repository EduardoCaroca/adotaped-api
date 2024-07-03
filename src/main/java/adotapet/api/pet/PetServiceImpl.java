package adotapet.api.pet;

import adotapet.api.pet.payload.PetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    @Override
    public List<PetDTO> listAllAvailable() {
        List<Pet> pets = repository.findAll();
        List<Pet> available = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdopted()) {
                available.add(pet);
            }
        }
        return available.stream().map(pet -> PetDTO.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .breed(pet.getBreed())
                        .age(pet.getAge())
                        .build())
                .toList();
    }
}

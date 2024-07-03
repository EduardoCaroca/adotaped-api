package adotapet.api.pet;

import adotapet.api.pet.payload.PetDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> listAllAvailable() {
        List<Pet> pets = repository.findAllByAdoptedFalse();
        return pets.stream().map(pet -> PetDTO.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .breed(pet.getBreed())
                        .age(pet.getAge())
                        .type(pet.getType())
                        .build())
                .toList();
    }
}

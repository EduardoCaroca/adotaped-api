package adotapet.api.pet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository repository;

    @Override
    public List<Pet> listAllAvailable() {
        List<Pet> pets = repository.findAll();
        List<Pet> available = new ArrayList<>();
        for (Pet pet : pets) {
            if (!pet.getAdopted()) {
                available.add(pet);
            }
        }
        return available;
    }
}

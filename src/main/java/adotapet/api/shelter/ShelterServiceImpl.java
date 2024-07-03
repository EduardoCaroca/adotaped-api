package adotapet.api.shelter;

import adotapet.api.model.exceptions.BadRequestException;
import adotapet.api.model.exceptions.ResourceNotFoundException;
import adotapet.api.pet.Pet;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository repository;

    @Override
    public List<Shelter> listAll() {
        return repository.findAll();
    }

    @Override
    public void register(Shelter shelter) {
        boolean nameAlreadyRegistered = repository.existsByName(shelter.getName());
        boolean phoneAlreadyRegistered = repository.existsByPhone(shelter.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(shelter.getEmail());

        if (nameAlreadyRegistered || phoneAlreadyRegistered || emailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another shelter!");
        } else {
            repository.save(shelter);
        }
    }

    @Override
    public List<Pet> listAllPets(String idOrName) {
        try {
            Long id = Long.parseLong(idOrName);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            return pets;
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Shelter not found!");
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = repository.findByName(idOrName).getPets();
                return pets;
            } catch (EntityNotFoundException enfe) {
                throw new ResourceNotFoundException("Shelter not found!");
            }
        }
    }

    @Override
    public void registerPet(String idOrName, Pet pet) {
        try {
            Long id = Long.parseLong(idOrName);
            Shelter shelter = repository.getReferenceById(id);
            pet.setShelter(shelter);
            pet.setAdopted(false);
            shelter.getPets().add(pet);
            repository.save(shelter);
        } catch (EntityNotFoundException enfe) {
            throw new ResourceNotFoundException("Shelter not found!");
        } catch (NumberFormatException nfe) {
            try {
                Shelter shelter = repository.findByName(idOrName);
                pet.setShelter(shelter);
                pet.setAdopted(false);
                shelter.getPets().add(pet);
                repository.save(shelter);
            } catch (EntityNotFoundException enfe) {
                throw new ResourceNotFoundException("Shelter not found!");
            }
        }
    }
}

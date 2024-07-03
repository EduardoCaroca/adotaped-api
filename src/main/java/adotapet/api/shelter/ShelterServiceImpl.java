package adotapet.api.shelter;

import adotapet.api.model.exceptions.BadRequestException;
import adotapet.api.model.exceptions.ResourceNotFoundException;
import adotapet.api.pet.Pet;
import adotapet.api.pet.payload.PetDTO;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.payload.ShelterDTO;
import adotapet.api.shelter.payload.ShelterForm;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository repository;

    @Override
    public List<ShelterDTO> listAll() {
        return repository.findAll().stream()
                .map(shelter -> ShelterDTO.builder()
                        .id(shelter.getId())
                        .name(shelter.getName())
                        .phone(shelter.getPhone())
                        .email(shelter.getEmail())
                        .build())
                .toList();
    }

    @Override
    public void register(ShelterForm form) {
        boolean nameAlreadyRegistered = repository.existsByName(form.getName());
        boolean phoneAlreadyRegistered = repository.existsByPhone(form.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(form.getEmail());

        if (nameAlreadyRegistered || phoneAlreadyRegistered || emailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another shelter!");
        } else {
            Shelter shelter = new Shelter(form);
            repository.save(shelter);
        }
    }

    @Override
    public List<PetDTO> listAllPets(String idOrName) {
        try {
            Long id = Long.parseLong(idOrName);
            List<Pet> pets = repository.getReferenceById(id).getPets();
            return pets.stream().map(pet -> PetDTO.builder()
                            .id(pet.getId())
                            .name(pet.getName())
                            .adopted(pet.getAdopted())
                            .build())
                    .toList();
        } catch (EntityNotFoundException enfe) {
            throw new EntityNotFoundException("Shelter not found!");
        } catch (NumberFormatException e) {
            try {
                List<Pet> pets = repository.findByName(idOrName).getPets();
                return pets.stream().map(pet -> PetDTO.builder()
                                .id(pet.getId())
                                .name(pet.getName())
                                .adopted(pet.getAdopted())
                                .build())
                        .toList();
            } catch (EntityNotFoundException enfe) {
                throw new ResourceNotFoundException("Shelter not found!");
            }
        }
    }

    @Override
    public void registerPet(String idOrName, PetForm form) {
        try {
            Long id = Long.parseLong(idOrName);
            Shelter shelter = repository.getReferenceById(id);
            Pet pet = new Pet(form);
            pet.update(shelter);
            repository.save(shelter);
        } catch (EntityNotFoundException enfe) {
            throw new ResourceNotFoundException("Shelter not found!");
        } catch (NumberFormatException nfe) {
            try {
                Shelter shelter = repository.findByName(idOrName);
                Pet pet = new Pet(form);
                pet.update(shelter);
                repository.save(shelter);
            } catch (EntityNotFoundException enfe) {
                throw new ResourceNotFoundException("Shelter not found!");
            }
        }
    }
}

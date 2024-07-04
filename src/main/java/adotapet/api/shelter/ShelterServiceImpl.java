package adotapet.api.shelter;

import adotapet.api.commom.domain.config.exceptions.BadRequestException;
import adotapet.api.pet.Pet;
import adotapet.api.pet.PetRepository;
import adotapet.api.pet.payload.PetDTO;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.payload.ShelterDTO;
import adotapet.api.shelter.payload.ShelterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository repository;
    private final PetRepository petRepository;

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public ShelterDTO register(ShelterForm form) {
        boolean nameOrPhoneOrEmailAlreadyRegistered = repository.existsByNameOrPhoneOrEmail(form.getName(), form.getPhone(), form.getEmail());

        if (nameOrPhoneOrEmailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another shelter!");
        }

        Shelter shelter = new Shelter(form);
        return ShelterDTO.builder()
                .id(repository.save(shelter).getId())
                .name(shelter.getName())
                .phone(shelter.getPhone())
                .email(shelter.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetDTO> listAllPets(Long id) {
        List<Pet> pets = repository.getReferenceById(id).getPets();
        return pets.stream().map(pet -> PetDTO.builder()
                        .id(pet.getId())
                        .name(pet.getName())
                        .adopted(pet.getAdopted())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public PetDTO registerPet(Long id, PetForm form) {
        Shelter shelter = repository.getReferenceById(id);
        Pet pet = new Pet(form);
        pet.update(shelter);
        return PetDTO.builder()
                .id(petRepository.save(pet).getId())
                .name(pet.getName())
                .build();
    }
}

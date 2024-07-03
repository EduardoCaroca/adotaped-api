package adotapet.api.shelter;

import adotapet.api.pet.Pet;

import java.util.List;

public interface ShelterService {

    List<Shelter> listAll();

    void register(Shelter shelter);

    List<Pet> listAllPets(String idOrName);

    void registerPet(String idOrName, Pet pet);
}

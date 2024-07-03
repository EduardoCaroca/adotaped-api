package adotapet.api.shelter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    boolean existsByNameOrPhoneOrEmail(String name, String phone, String email);
    
}
package adotapet.api.shelter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    boolean existsByName(String name);

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    Shelter findByName(String name);
}
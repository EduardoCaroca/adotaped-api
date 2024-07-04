package adotapet.api.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByAdoptedFalse();

    boolean existsByIdAndAdoptedTrue(Long id);

}

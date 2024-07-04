package adotapet.api.adoption;

import adotapet.api.commom.domain.model.enums.AdoptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {

    boolean existsAllByGuardian_IdAndStatus(Long guardianId, AdoptionStatus status);

    boolean existsAllByPet_IdAndStatus(Long petId, AdoptionStatus status);

    int countAllByGuardian_IdAndStatus(Long guardianId, AdoptionStatus status);

}

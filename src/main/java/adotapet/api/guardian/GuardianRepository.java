package adotapet.api.guardian;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

}
package adotapet.api.guardian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    boolean existsByPhoneOrEmail(String phone, String email);

}
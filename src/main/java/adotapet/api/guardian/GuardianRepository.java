package adotapet.api.guardian;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    boolean existsByPhoneOrEmail(String phone, String email);

    boolean existsByEmail(String email);

}
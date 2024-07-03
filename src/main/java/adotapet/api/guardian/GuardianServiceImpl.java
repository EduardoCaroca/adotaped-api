package adotapet.api.guardian;

import adotapet.api.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository repository;

    @Override
    public void register(Guardian guardian) {
        boolean phoneAlreadyRegistered = repository.existsByPhone(guardian.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(guardian.getEmail());

        if (phoneAlreadyRegistered || emailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another guardian!");
        } else {
            repository.save(guardian);
        }
    }

    @Override
    public void update(Guardian guardian) {
        repository.save(guardian);
    }
}

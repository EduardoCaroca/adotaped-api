package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianForm;
import adotapet.api.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository repository;

    @Override
    public void register(GuardianForm form) {
        boolean phoneAlreadyRegistered = repository.existsByPhone(form.getPhone());
        boolean emailAlreadyRegistered = repository.existsByEmail(form.getEmail());

        if (phoneAlreadyRegistered || emailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another guardian!");
        } else {
            Guardian guardian = new Guardian(form);
            repository.save(guardian);
        }
    }

    @Override
    public void update(Long id, GuardianForm form) {
        Guardian guardian = repository.findById(id).orElseThrow(() -> new BadRequestException("Guardian not found!"));
        guardian.update(form);
        repository.save(guardian);
    }
}

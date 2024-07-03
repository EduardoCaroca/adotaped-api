package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianDTO;
import adotapet.api.guardian.payload.GuardianForm;
import adotapet.api.model.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository repository;

    @Override
    @Transactional
    public GuardianDTO register(GuardianForm form) {
        boolean phoneOrEmailAlreadyRegistered = repository.existsByPhoneOrEmail(form.getPhone(), form.getEmail());

        if (phoneOrEmailAlreadyRegistered) {
            throw new BadRequestException("Data already registered for another guardian!");
        }

        Guardian guardian = new Guardian(form);
        return GuardianDTO.builder()
                .id(repository.save(guardian).getId())
                .name(guardian.getName())
                .phone(guardian.getPhone())
                .email(guardian.getEmail())
                .build();
    }

    @Override
    @Transactional
    public GuardianDTO update(Long id, GuardianForm form) {
        Guardian guardian = repository.findById(id).orElseThrow(() -> new BadRequestException("Guardian not found!"));
        guardian.update(form);
        repository.save(guardian);
        return GuardianDTO.builder()
                .id(guardian.getId())
                .name(guardian.getName())
                .phone(guardian.getPhone())
                .email(guardian.getEmail())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuardianDTO> listAll() {
        List<Guardian> guardians = repository.findAll();
        return guardians.stream()
                .map(guardian -> GuardianDTO.builder()
                        .id(guardian.getId())
                        .name(guardian.getName())
                        .phone(guardian.getPhone())
                        .email(guardian.getEmail())
                        .build())
                .toList();
    }
}

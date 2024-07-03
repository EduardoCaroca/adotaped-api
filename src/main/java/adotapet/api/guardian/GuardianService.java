package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianDTO;
import adotapet.api.guardian.payload.GuardianForm;

import java.util.List;

public interface GuardianService {

    GuardianDTO register(GuardianForm form);

    GuardianDTO update(Long id, GuardianForm form);

    List<GuardianDTO> listAll();
}

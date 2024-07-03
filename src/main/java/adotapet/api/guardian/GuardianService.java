package adotapet.api.guardian;

import adotapet.api.guardian.payload.GuardianForm;

public interface GuardianService {

    void register(GuardianForm form);

    void update(Long id, GuardianForm form);

}

package adotapet.api.adoption;

import adotapet.api.adoption.payload.AdoptionForm;

public interface AdoptionService {

    void request(AdoptionForm form);

    void approve(Long id);

    void reject(Long id);

}

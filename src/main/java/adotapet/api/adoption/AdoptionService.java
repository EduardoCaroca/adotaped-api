package adotapet.api.adoption;

public interface AdoptionService {

    void request(Adoption adoption);

    void approve(Adoption adoption);

    void reject(Adoption adoption);

}

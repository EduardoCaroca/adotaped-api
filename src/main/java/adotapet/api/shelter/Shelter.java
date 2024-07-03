package adotapet.api.shelter;

import adotapet.api.pet.Pet;
import adotapet.api.shelter.payload.ShelterForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelters")
@Getter
@Setter
@NoArgsConstructor
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    public Shelter(ShelterForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.email = form.getEmail();
    }

    public void update(ShelterForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.email = form.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package adotapet.api.pet;

import adotapet.api.adoption.Adoption;
import adotapet.api.model.enums.PetType;
import adotapet.api.pet.payload.PetForm;
import adotapet.api.shelter.Shelter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    private String breed;

    private Integer age;

    private String color;

    private Float weight;

    private Boolean adopted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shelter shelter;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL, optional = false)
    private Adoption adoption;

    public Pet(PetForm form) {
        this.type = form.getType();
        this.name = form.getName();
        this.breed = form.getBreed();
        this.age = form.getAge();
        this.color = form.getColor();
        this.weight = form.getWeight();
        this.adopted = false;
    }

    public void update(PetForm form) {
        this.type = form.getType();
        this.name = form.getName();
        this.breed = form.getBreed();
        this.age = form.getAge();
        this.color = form.getColor();
        this.weight = form.getWeight();
    }

    public void update(Shelter shelter) {
        this.shelter = shelter;
        shelter.getPets().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

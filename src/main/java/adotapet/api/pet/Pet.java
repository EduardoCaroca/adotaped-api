package adotapet.api.pet;

import adotapet.api.adoption.Adoption;
import adotapet.api.model.enums.PetType;
import adotapet.api.shelter.Shelter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "pets")
@Getter
@Setter
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type")
    private PetType type;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "breed")
    private String breed;

    @NotNull
    @Column(name = "age")
    private Integer age;

    @NotBlank
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "weight")
    private Float weight;

    @Column(name = "adopted")
    private Boolean adopted;

    @ManyToOne
    @JsonBackReference("shelter_pets")
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @OneToOne(mappedBy = "pet")
    @JsonBackReference("adoption_pets")
    private Adoption adoption;

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

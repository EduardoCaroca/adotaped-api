package adotapet.api.adoption;

import adotapet.api.guardian.Guardian;
import adotapet.api.model.enums.AdoptionStatus;
import adotapet.api.pet.Pet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adocoes")
@Getter
@Setter
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @NotNull
    @ManyToOne
    @JsonBackReference("guardian_adoptions")
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @NotNull
    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonManagedReference("adoption_pets")
    private Pet pet;

    @NotBlank
    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AdoptionStatus status;

    @Column(name = "status_justification")
    private String statusJustification;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adoption adoption = (Adoption) o;
        return Objects.equals(id, adoption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

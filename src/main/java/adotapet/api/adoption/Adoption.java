package adotapet.api.adoption;

import adotapet.api.adoption.payload.AdoptionForm;
import adotapet.api.guardian.Guardian;
import adotapet.api.model.enums.AdoptionStatus;
import adotapet.api.pet.Pet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adocoes")
@Getter
@Setter
@NoArgsConstructor
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @ManyToOne
    @JsonBackReference("guardian_adoptions")
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonManagedReference("adoption_pets")
    private Pet pet;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;

    private String statusJustification;

    public Adoption(AdoptionForm adoptionForm) {
        this.date = LocalDateTime.now();
        this.reason = adoptionForm.getReason();
        this.status = AdoptionStatus.PENDING_EVALUATION;
    }

    public void update(Pet pet) {
        this.pet = pet;
    }

    public void update(Guardian guardian) {
        this.guardian = guardian;
    }

    public void approve() {
        this.status = AdoptionStatus.APPROVED;
    }

    public void reject() {
        this.status = AdoptionStatus.REJECTED;
    }

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

package adotapet.api.guardian;

import adotapet.api.adoption.Adoption;
import adotapet.api.guardian.payload.GuardianForm;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@NoArgsConstructor
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "guardian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Adoption> adoptions = new ArrayList<>();

    public Guardian(GuardianForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.email = form.getEmail();
    }

    public void update(GuardianForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.email = form.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guardian guardian = (Guardian) o;
        return Objects.equals(id, guardian.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

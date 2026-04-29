package house.wammys.youpronoapi.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sport extends BasePostgreEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public Sport(String name) {
        this.name = name;
    }
}
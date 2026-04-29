package house.wammys.youpronoapi.model.postgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class League extends BasePostgreEntity {

    @Column(nullable = false, unique = true)
    private String name;
    private Long apiId;
    private String logoUrl;

    @ManyToOne
    private Sport sport;

    public League(String name, Long id, String logoUrl, Sport sport) {
        this.name = name;
        this.apiId = id;
        this.logoUrl = logoUrl;
        this.sport = sport;
    }
}

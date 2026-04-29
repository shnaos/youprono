package house.wammys.youpronoapi.model.postgres;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team extends BasePostgreEntity {

    private String name;
    private String shortName;
    private String logoUrl;
    private Long apiId;

    @ManyToOne
    private Sport sport;

    public Team(String name, String shortName, String logoUrl, Sport sport, Long apiId) {
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.sport = sport;
        this.apiId = apiId;
    }

    public void setApiTeamId(Long id) {
        this.apiId = id;
    }
}
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
public class Player extends BasePostgreEntity {
    private String name;
    private String position;

    @ManyToOne
    private Team team;

    public Player(String name, String position, Team team) {
        this.name = name;
        this.position = position;
        this.team = team;
    }
}
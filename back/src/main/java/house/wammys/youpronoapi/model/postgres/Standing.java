package house.wammys.youpronoapi.model.postgres;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Standing extends BasePostgreEntity {

    private int rank;

    @ManyToOne(optional = false)
    private League league;

    @ManyToOne(optional = false)
    private Team team;

    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer points;
    private Integer goalDifference;
    private Integer pointsScored;
    private int season;
    private LocalDate date;
}

package house.wammys.youpronoapi.model.postgres;

import java.time.LocalDateTime;

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
public class Match extends BasePostgreEntity {

    @Column(nullable = false, unique = true) // Empêche les doublons d'ID API
    private Long apiMatchId;

    @ManyToOne(optional = false)
    private Sport sport;

    @ManyToOne(optional = false)
    private Team teamHome;

    @ManyToOne(optional = false)
    private Team teamAway;

    @ManyToOne(optional = false)
    private League league;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private String location;

    // Résultat (null au début)
    private Integer scoreHome;
    private Integer scoreAway;

    public Match(Sport sport, Team teamHome, Team teamAway, LocalDateTime startTime, String location, League league) {
        this.sport = sport;
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        this.startTime = startTime;
        this.location = location;
        this.league = league;
        this.scoreHome = null;
        this.scoreAway = null;
    }
}

package house.wammys.youpronoapi.service;

import java.time.LocalDate;
import java.util.List;

import house.wammys.youpronoapi.model.postgres.League;
import house.wammys.youpronoapi.model.postgres.Match;
import house.wammys.youpronoapi.model.postgres.Player;
import house.wammys.youpronoapi.model.postgres.Team;

public interface SportService {
    List<Match> getMatches(LocalDate date);

    List<Team> getTeams();

    List<League> getLeagues();

    List<Player> getPlayers();
}

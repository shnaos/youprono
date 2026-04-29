package house.wammys.youpronoapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import house.wammys.youpronoapi.model.postgres.League;
import house.wammys.youpronoapi.model.postgres.Match;
import house.wammys.youpronoapi.model.postgres.Team;
import house.wammys.youpronoapi.repository.postgres.LeagueRepository;
import house.wammys.youpronoapi.repository.postgres.MatchRepository;
import house.wammys.youpronoapi.repository.postgres.TeamRepository;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final RestTemplate restTemplate;

    @Value("${API_SPORTS_KEY}")
    private String apiKey;

    @Value("${API_SPORTS_BASKETBALL_URL}")
    private String apiUrl;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository,
            LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndSaveTomorrowMatches() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String url = String.format("%s/fixtures?date=%s", apiUrl, tomorrow);

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> matches = (List<Map<String, Object>>) response.getBody().get("response");

            for (Map<String, Object> matchData : matches) {
                Map<String, Object> fixture = (Map<String, Object>) matchData.get("fixture");
                Map<String, Object> teams = (Map<String, Object>) matchData.get("teams");
                Map<String, Object> leagueData = (Map<String, Object>) matchData.get("league");

                Long apiMatchId = ((Number) fixture.get("id")).longValue();
                String homeTeamName = (String) ((Map<String, Object>) teams.get("home")).get("name");
                String awayTeamName = (String) ((Map<String, Object>) teams.get("away")).get("name");
                LocalDateTime matchDate = LocalDateTime.parse((String) fixture.get("date"));
                String leagueName = (String) leagueData.get("name");

                // Vérifier si le match existe déjà
                if (matchRepository.findByApiMatchId(apiMatchId).isEmpty()) {
                    Match match = new Match();
                    match.setPublicId(UUID.randomUUID()); // Génération de l'UUID public
                    match.setApiMatchId(apiMatchId);

                    // Vérifier ou créer l'équipe domicile
                    Team homeTeam = teamRepository.findByName(homeTeamName).orElseGet(() -> {
                        Team newTeam = new Team();
                        newTeam.setName(homeTeamName);
                        return teamRepository.save(newTeam);
                    });

                    // Vérifier ou créer l'équipe extérieure
                    Team awayTeam = teamRepository.findByName(awayTeamName).orElseGet(() -> {
                        Team newTeam = new Team();
                        newTeam.setName(awayTeamName);
                        return teamRepository.save(newTeam);
                    });

                    match.setTeamHome(homeTeam);
                    match.setTeamAway(awayTeam);
                    match.setStartTime(matchDate);

                    // Vérifier ou créer la ligue
                    League matchLeague = leagueRepository.findByName(leagueName).orElseGet(() -> {
                        League newLeague = new League();
                        newLeague.setName(leagueName);
                        return leagueRepository.save(newLeague);
                    });

                    match.setLeague(matchLeague);

                    matchRepository.save(match);
                }
            }
        }
    }
}

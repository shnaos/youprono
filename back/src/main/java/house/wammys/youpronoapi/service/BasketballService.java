package house.wammys.youpronoapi.service;

import house.wammys.youpronoapi.model.postgres.Match;
import house.wammys.youpronoapi.model.postgres.Player;
import house.wammys.youpronoapi.model.postgres.Sport;
import house.wammys.youpronoapi.model.postgres.Team;
import house.wammys.youpronoapi.repository.postgres.LeagueRepository;
import house.wammys.youpronoapi.repository.postgres.MatchRepository;
import house.wammys.youpronoapi.repository.postgres.SportRepository;
import house.wammys.youpronoapi.repository.postgres.TeamRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import house.wammys.youpronoapi.model.postgres.League;

@Service
@Slf4j
public class BasketballService implements SportService {

    private final SportRepository sportRepository;
    private final LeagueRepository leagueRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final RestTemplate restTemplate;

    @Value("${api-sports.basketball.url}")
    private String apiUrl;

    @Value("${api-sports.key}")
    private String apiKey;

    public BasketballService(MatchRepository matchRepository,
            TeamRepository teamRepository,
            LeagueRepository leagueRepository,
            SportRepository sportRepository) {
        this.sportRepository = sportRepository;
        this.matchRepository = matchRepository;
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<Match> getMatches(LocalDate date) {
        String url = String.format("%s/games?date=%s", apiUrl, date);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        List<Match> matchesList = new ArrayList<>();

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> matches = (List<Map<String, Object>>) response.getBody().get("response");

            for (Map<String, Object> matchData : matches) {
                try {
                    // Vérifier que toutes les infos nécessaires sont là
                    if (matchData.get("id") == null || matchData.get("date") == null || matchData.get("teams") == null
                            || matchData.get("league") == null) {
                        log.warn("⛔ Données incomplètes pour un match : {}", matchData);
                        continue;
                    }

                    Long apiMatchId = ((Number) matchData.get("id")).longValue();
                    LocalDateTime matchDate = LocalDateTime.parse((String) matchData.get("date"),
                            DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                    Map<String, Object> teams = (Map<String, Object>) matchData.get("teams");
                    Map<String, Object> homeTeamMap = (Map<String, Object>) teams.get("home");
                    Map<String, Object> awayTeamMap = (Map<String, Object>) teams.get("away");

                    if (homeTeamMap == null || awayTeamMap == null) {
                        log.warn("⛔ Données incomplètes sur les équipes : {}", matchData);
                        continue;
                    }

                    String homeTeamName = (String) homeTeamMap.get("name");
                    String awayTeamName = (String) awayTeamMap.get("name");

                    Map<String, Object> leagueData = (Map<String, Object>) matchData.get("league");
                    String leagueName = (String) leagueData.get("name");

                    Team homeTeam = getOrCreateTeam(
                            ((Number) homeTeamMap.get("id")).longValue(),
                            homeTeamName,
                            (String) homeTeamMap.get("code"),
                            (String) homeTeamMap.get("logo"));

                    Team awayTeam = getOrCreateTeam(
                            ((Number) awayTeamMap.get("id")).longValue(),
                            awayTeamName,
                            (String) awayTeamMap.get("code"),
                            (String) awayTeamMap.get("logo"));

                    League league = getOrCreateLeague(
                            ((Number) leagueData.get("id")).longValue(),
                            leagueName,
                            (String) leagueData.get("logo"));

                    if (matchRepository.findByApiMatchId(apiMatchId).isEmpty()) {
                        Sport basketball = getOrCreateSport("Basketball");
                        Match match = new Match(basketball, homeTeam, awayTeam, matchDate, "Unknown", league);
                        match.setPublicId(UUID.randomUUID());
                        match.setApiMatchId(apiMatchId);
                        matchRepository.save(match);
                        matchesList.add(match);
                    }
                } catch (Exception e) {
                    log.warn("⛔ Erreur lors du parsing du match : {}", matchData, e);
                }
            }
        }

        return matchesList;
    }

    public void fetchAndSaveTeams() {
        String url = String.format("%s/teams?league=12&season=2023-2024", apiUrl); // ex: NBA = 12
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apisports-key", apiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> teams = (List<Map<String, Object>>) response.getBody().get("response");

            for (Map<String, Object> teamData : teams) {
                try {
                    Map<String, Object> team = (Map<String, Object>) teamData.get("team");

                    Long apiTeamId = ((Number) team.get("id")).longValue();
                    String name = (String) team.get("name");
                    String shortName = (String) team.get("code");
                    String logoUrl = (String) team.get("logo");

                    teamRepository.findByApiId(apiTeamId)
                            .orElseGet(() -> teamRepository.save(new Team(name, shortName, logoUrl, null, apiTeamId)));

                } catch (Exception e) {
                    log.warn("⚠️ Erreur lors de l'enregistrement d'une équipe : {}", teamData, e);
                }
            }
        } else {
            log.warn("❌ Échec de la récupération des équipes.");
        }
    }

    private Team getOrCreateTeam(Long apiTeamId, String name, String shortName, String logoUrl) {
        return teamRepository.findByApiId(apiTeamId)
                .orElseGet(() -> {
                    Team newTeam = new Team(name, shortName, logoUrl, null, apiTeamId);
                    newTeam.setApiTeamId(apiTeamId);
                    return teamRepository.save(newTeam);
                });
    }

    private League getOrCreateLeague(Long apiLeagueId, String name, String logoUrl) {
        Sport basketball = getOrCreateSport("Basketball");
        return leagueRepository.findByApiId(apiLeagueId)
                .orElseGet(() -> leagueRepository.save(new League(name, apiLeagueId, logoUrl, basketball)));
    }

    private Sport getOrCreateSport(String sportName) {
        return sportRepository.findByName(sportName)
                .orElseGet(() -> sportRepository.save(new Sport(sportName)));
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Override
    public List<League> getLeagues() {
        return leagueRepository.findAll();
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>();
    }
}

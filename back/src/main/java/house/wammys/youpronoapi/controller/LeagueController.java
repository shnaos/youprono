package house.wammys.youpronoapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import house.wammys.youpronoapi.dto.LeagueDto;
import house.wammys.youpronoapi.model.postgres.League;
import house.wammys.youpronoapi.repository.postgres.LeagueRepository;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueRepository leagueRepository;

    public LeagueController(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    @GetMapping
    public ResponseEntity<List<LeagueDto>> getLeagues(@RequestParam(required = false) String sport) {
        List<League> leagues = sport != null
                ? leagueRepository.findBySportName(sport)
                : leagueRepository.findAll();

        return ResponseEntity.ok(leagues.stream().map(LeagueDto::from).toList());
    }
}

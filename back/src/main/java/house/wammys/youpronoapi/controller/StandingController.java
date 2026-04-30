package house.wammys.youpronoapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.wammys.youpronoapi.dto.StandingDto;
import house.wammys.youpronoapi.repository.postgres.StandingRepository;

@RestController
@RequestMapping("/api/standings")
public class StandingController {

    private final StandingRepository standingRepository;

    public StandingController(StandingRepository standingRepository) {
        this.standingRepository = standingRepository;
    }

    @GetMapping("/league/{id}")
    public ResponseEntity<List<StandingDto>> getStandingsByLeague(@PathVariable Long id) {
        List<StandingDto> standings = standingRepository.findByLeagueId(id)
                .stream()
                .map(StandingDto::from)
                .toList();
        return ResponseEntity.ok(standings);
    }
}

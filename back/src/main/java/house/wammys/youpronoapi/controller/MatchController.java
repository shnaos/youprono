package house.wammys.youpronoapi.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import house.wammys.youpronoapi.dto.MatchDto;
import house.wammys.youpronoapi.model.postgres.Match;
import house.wammys.youpronoapi.repository.postgres.MatchRepository;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping("/matches")
    public ResponseEntity<Page<MatchDto>> getMatches(
            @RequestParam(required = false) String sport,
            @RequestParam(required = false) Long leagueId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Match> matches;
        if (sport != null && leagueId != null) {
            matches = matchRepository.findBySportNameAndLeagueId(sport, leagueId, pageable);
        } else if (sport != null) {
            matches = matchRepository.findBySportName(sport, pageable);
        } else if (leagueId != null) {
            matches = matchRepository.findByLeagueId(leagueId, pageable);
        } else {
            matches = matchRepository.findAll(pageable);
        }

        return ResponseEntity.ok(matches.map(MatchDto::from));
    }

    @GetMapping("/{publicId}")
    public ResponseEntity<MatchDto> getMatchByPublicId(@PathVariable UUID publicId) {
        return matchRepository.findByPublicId(publicId)
                .map(MatchDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

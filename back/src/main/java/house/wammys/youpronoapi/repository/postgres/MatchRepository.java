package house.wammys.youpronoapi.repository.postgres;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Match;

@Repository
public interface MatchRepository extends BasePostgresRepository<Match, Long> {
    Optional<Match> findByApiMatchId(Long apiMatchId);

    Page<Match> findBySportName(String sportName, Pageable pageable);

    Page<Match> findByLeagueId(Long leagueId, Pageable pageable);

    Page<Match> findBySportNameAndLeagueId(String sportName, Long leagueId, Pageable pageable);
}

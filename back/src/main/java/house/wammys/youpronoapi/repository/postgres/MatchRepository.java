package house.wammys.youpronoapi.repository.postgres;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Match;

@Repository
public interface MatchRepository extends BasePostgresRepository<Match, Long> {
    Optional<Match> findByApiMatchId(Long apiMatchId);
}

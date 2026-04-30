package house.wammys.youpronoapi.repository.postgres;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.League;

@Repository
public interface LeagueRepository extends BasePostgresRepository<League, Long> {
    Optional<League> findByName(String name);

    Optional<League> findByApiId(Number id);

    List<League> findBySportName(String sportName);
}

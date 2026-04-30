package house.wammys.youpronoapi.repository.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Standing;

@Repository
public interface StandingRepository extends BasePostgresRepository<Standing, Long> {

    List<Standing> findByLeagueId(Long leagueId);
}

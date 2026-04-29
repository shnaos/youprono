package house.wammys.youpronoapi.repository.postgres;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Team;

@Repository
public interface TeamRepository extends BasePostgresRepository<Team, Long> {
    Optional<Team> findByName(String name);

    Optional<Team> findByApiId(Long id);
}

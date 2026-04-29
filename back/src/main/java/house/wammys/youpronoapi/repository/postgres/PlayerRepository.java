package house.wammys.youpronoapi.repository.postgres;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Player;

@Repository
public interface PlayerRepository extends BasePostgresRepository<Player, Long> {
    Optional<Player> findById(int id);
}

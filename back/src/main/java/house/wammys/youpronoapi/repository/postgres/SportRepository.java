package house.wammys.youpronoapi.repository.postgres;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import house.wammys.youpronoapi.model.postgres.Sport;

@Repository
public interface SportRepository extends BasePostgresRepository<Sport, Long> {
    Optional<Sport> findByName(String name);
}
package house.wammys.youpronoapi.repository.postgres;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BasePostgresRepository<T, ID> extends JpaRepository<T, ID> {

    // Recherche par UUID (exposé publiquement)
    Optional<T> findByPublicId(UUID publicId);

    // Suppression par UUID
    void deleteByPublicId(UUID publicId);
}

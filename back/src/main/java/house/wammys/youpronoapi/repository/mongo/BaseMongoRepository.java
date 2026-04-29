package house.wammys.youpronoapi.repository.mongo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseMongoRepository<T, ID> extends MongoRepository<T, ID> {

    // Recherche par UUID (publicId)
    Optional<T> findByPublicId(UUID publicId);

    // Suppression par publicId
    void deleteByPublicId(UUID publicId);
}

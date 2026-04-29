package house.wammys.youpronoapi.model.mongo;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseMongoEntity {

    @Id
    private String id; // Identifiant MongoDB (String)

    private UUID publicId; // UUID public pour sécuriser les échanges

    // Constructeur par défaut qui initialise le UUID
    public BaseMongoEntity() {
        this.publicId = UUID.randomUUID();
    }
}

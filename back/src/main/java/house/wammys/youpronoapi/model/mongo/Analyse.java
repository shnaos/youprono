package house.wammys.youpronoapi.model.mongo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "analyses")
@Getter
@Setter
@NoArgsConstructor
public class Analyse extends BaseMongoEntity {

    @Field("matchPublicId")
    private UUID matchPublicId;

    @Field("summary")
    private String summary;

    @Field("keyStats")
    private List<String> keyStats;

    @Field("prediction")
    private String prediction; // ex: "Victoire de l’équipe A"

    @Field("generatedAt")
    private LocalDateTime generatedAt;

    public Analyse(UUID matchPublicId, String summary, List<String> keyStats, String prediction) {
        super();
        this.matchPublicId = matchPublicId;
        this.summary = summary;
        this.keyStats = keyStats;
        this.prediction = prediction;
        this.generatedAt = LocalDateTime.now();
    }
}

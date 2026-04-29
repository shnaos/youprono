package house.wammys.youpronoapi.cron;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import house.wammys.youpronoapi.model.postgres.Match;
import house.wammys.youpronoapi.service.BasketballService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MatchScheduler {

    private final BasketballService basketballService;

    @Value("${scheduling.cron.fetch-matches}")
    private String fetchMatchesCron;

    @Value("${scheduling.cron.fetch-teams}")
    private String fetchTeamsCron;

    public MatchScheduler(BasketballService basketballService) {
        this.basketballService = basketballService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fetchDailyMatches() {
        log.info("🟡 [CRON] fetchDailyMatches lancé");
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Match> matches = basketballService.getMatches(tomorrow);

        if (!matches.isEmpty()) {
            log.info("✅ [CRON] fetchDailyTeams terminé avec succès");
            log.info(matches.size() + " matchs NBA ont été récupérés et enregistrés.");
        } else {
            System.out.println("Aucun match trouvé pour demain.");
        }
    }

    @Scheduled(cron = "${scheduling.cron.fetch-teams}")
    public void fetchMonthlyTeams() {
        log.info("🟡 [CRON] fetchDailyTeams lancé");

        try {
            basketballService.getTeams();
            log.info("✅ [CRON] fetchDailyTeams terminé avec succès");
        } catch (Exception e) {
            log.error("❌ [CRON] fetchDailyTeams a échoué", e);
        }
    }
}

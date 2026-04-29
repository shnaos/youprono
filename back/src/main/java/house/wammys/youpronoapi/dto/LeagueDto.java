package house.wammys.youpronoapi.dto;

import house.wammys.youpronoapi.model.postgres.League;

public record LeagueDto(
        Long id,
        String publicId,
        String name,
        String slug,
        String country,
        String logoPath,
        String sport) {

    public static LeagueDto from(League league) {
        return new LeagueDto(
                league.getId(),
                league.getPublicId().toString(),
                league.getName(),
                league.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-"),
                null,
                league.getLogoUrl(),
                league.getSport() != null ? league.getSport().getName() : null);
    }
}

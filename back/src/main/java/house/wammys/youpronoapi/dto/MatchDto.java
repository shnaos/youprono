package house.wammys.youpronoapi.dto;

import house.wammys.youpronoapi.model.postgres.Match;

public record MatchDto(
        String publicId,
        String sportName,
        String teamHome,
        String teamAway,
        String logoMiniPathTeamHome,
        String logoMiniPathTeamAway,
        String startTime,
        String location,
        Integer scoreHome,
        Integer scoreAway,
        LeagueInfo league) {

    public record LeagueInfo(Long id, String name, String country, String logoPath) {
    }

    public static MatchDto from(Match match) {
        LeagueInfo leagueInfo = new LeagueInfo(
                match.getLeague().getId(),
                match.getLeague().getName(),
                null,
                match.getLeague().getLogoUrl());

        return new MatchDto(
                match.getPublicId().toString(),
                match.getSport() != null ? match.getSport().getName() : null,
                match.getTeamHome().getName(),
                match.getTeamAway().getName(),
                match.getTeamHome().getLogoUrl(),
                match.getTeamAway().getLogoUrl(),
                match.getStartTime().toString(),
                match.getLocation(),
                match.getScoreHome(),
                match.getScoreAway(),
                leagueInfo);
    }
}

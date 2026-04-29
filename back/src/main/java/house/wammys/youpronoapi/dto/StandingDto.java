package house.wammys.youpronoapi.dto;

import house.wammys.youpronoapi.model.postgres.Standing;

public record StandingDto(
        Long id,
        String publicId,
        int rank,
        Long leagueId,
        Integer wins,
        Integer losses,
        Integer draws,
        Integer points,
        Integer goalDifference,
        Integer pointsScored,
        int season,
        String date,
        TeamInfo team) {

    public record TeamInfo(Long id, String publicId, String name, String shortName, String logoPath) {
    }

    public static StandingDto from(Standing standing) {
        TeamInfo teamInfo = new TeamInfo(
                standing.getTeam().getId(),
                standing.getTeam().getPublicId().toString(),
                standing.getTeam().getName(),
                standing.getTeam().getShortName(),
                standing.getTeam().getLogoUrl());

        return new StandingDto(
                standing.getId(),
                standing.getPublicId().toString(),
                standing.getRank(),
                standing.getLeague().getId(),
                standing.getWins(),
                standing.getLosses(),
                standing.getDraws(),
                standing.getPoints(),
                standing.getGoalDifference(),
                standing.getPointsScored(),
                standing.getSeason(),
                standing.getDate() != null ? standing.getDate().toString() : null,
                teamInfo);
    }
}

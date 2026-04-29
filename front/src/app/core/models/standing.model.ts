import { Form } from "./form.model";
import { Team } from "./team.model";

export interface Standing {
  id: number;
  publicId: string;
  rank: number;
  leagueId: number;
  losses: number | null;
  points: number | null;
  draws: number | null;
  goalDifference: number | null;
  pointsScored: number | null;
  season: number;
  team: Team;
  wins: number | null;
  date: Date;
}


import { League } from "./League.model";
import { Match } from "./match.model";
import { Team } from "./team.model";

export interface Sport {
  id: number;
  publicId: string;
  name: string;
  slug: string;
  leagues: League[];
  matches: Match[];
  teams: Team[];

}

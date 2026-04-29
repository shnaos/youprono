import { Match } from "./match.model"
import { Standing } from "./standing.model"
import { Team } from "./team.model"

export interface Form {
  id: string
  value: string // V/N/D
  matchId: Match
  team: Team
  standing: Standing
}

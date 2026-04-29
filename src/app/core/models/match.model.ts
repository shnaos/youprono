export interface Match {
  publicId: string;
  sport: any;
  teamHome: string;
  teamAway: string;
  logoMiniPathTeamHome: string;
  logoMiniPathTeamAway: string;
  startTime: string;
  location?: string;
  scoreHome?: number;
  scoreAway?: number;
  league: {
    id: number;       // 👈 Ajouté
    name: string;
    country: string;
    logoPath?: string;
  };
  sportName?: string;
  description?: string;
}

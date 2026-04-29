import { Sport } from "./sport.model";

export interface League {
  id: number;
  publicId: string;
  name: string;
  slug: string;
  country: string;
  logoPath: string | null;
  sport: Sport;
}

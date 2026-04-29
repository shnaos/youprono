import { Form } from "./form.model";
import { Sport } from "./sport.model";
import { Standing } from "./standing.model";


export interface Team {
  id: number;
  publicId: string;
  logoPath: string | null;
  logoMiniPath: string | null;
  name: string | null;
  shortName: string | null;
  standings: Standing[];
  sport: Sport;
  forms: Form[];
}

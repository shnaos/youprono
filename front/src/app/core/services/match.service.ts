import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core"; // ← NE PAS OUBLIER !
import { Observable } from "rxjs";
import { PaginatedResponse } from '../../features/list-match/paginated-reponse';
import { League } from "../models/League.model";
import { Match } from "../models/match.model";

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private baseUrl: string;

  constructor(
    private http: HttpClient
  ) {
    this.baseUrl = '/api';

  }

  getMatchesWithFilters(params: {
    page: number;
    size: number;
    sortBy: string;
    direction: string;
    sport?: string;
    leagueId?: number;
  }): Observable<PaginatedResponse<Match>> {

    let httpParams = new HttpParams()


    if (params.sport) httpParams = httpParams.set('sport', params.sport);
    if (params.leagueId) httpParams = httpParams.set('leagueId', params.leagueId.toString());
    if (params.page !== undefined) httpParams = httpParams.set('page', params.page.toString());
    if (params.size) httpParams = httpParams.set('size', params.size.toString());
    if (params.sortBy) httpParams = httpParams.set('sortBy', params.sortBy);
    if (params.direction) httpParams = httpParams.set('direction', params.direction);

    return this.http.get<PaginatedResponse<Match>>(`${this.baseUrl}/match/matches`, { params: httpParams });
  }

  getMatchById(publicId: string): Observable<Match> {
    return this.http.get<Match>(`${this.baseUrl}/match/${publicId}`);
  }

  getLeaguesBySport(sport: string): Observable<League[]> {
    const params = new HttpParams().set('sport', sport);
    return this.http.get<League[]>(`${this.baseUrl}/leagues`, { params });
  }
}

// src/app/core/services/standing.service.ts
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Standing } from '../models/standing.model';

@Injectable({
  providedIn: 'root'
})
export class StandingService {
  private readonly API_URL = '/api/standings';

  constructor(private http: HttpClient) { }

  getStandingByLeague(leagueId: number): Observable<Standing[]> {
    return this.http.get<Standing[]>(`${this.API_URL}/league/${leagueId}`);
  }
}

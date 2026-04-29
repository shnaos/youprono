import { Routes } from '@angular/router';
import { MatchDetailComponent } from './match-detail.component';

export const MATCH_ROUTES: Routes = [
  {
    path: ':publicId',
    component: MatchDetailComponent
  }
];

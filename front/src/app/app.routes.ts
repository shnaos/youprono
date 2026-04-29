import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./features/home/home.routes').then(m => m.HOME_ROUTES)

  },
  {
    path: 'matches',
    loadChildren: () =>
      import('./features/list-match/list-match.routes').then(m => m.LIST_MATCH_ROUTES)
  },
  {
    path: 'match',
    loadChildren: () =>
      import('./features/match-detail/match.routes').then(m => m.MATCH_ROUTES)
  },
  {
    path: '**',
    redirectTo: ''
  }
];

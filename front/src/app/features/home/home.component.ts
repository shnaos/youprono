import { Component, OnInit, CUSTOM_ELEMENTS_SCHEMA, inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser, CommonModule, DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatchService } from '../../core/services/match.service';
import { Match } from '../../core/models/match.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, DatePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  private matchService = inject(MatchService);
  private platformId = inject(PLATFORM_ID);

  upcomingMatches: Match[] = [];
  loading = true;
  error = false;

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      import('swiper/element/bundle').then(({ register }) => register());
    }
    this.loadUpcomingMatches();
  }

  loadUpcomingMatches(): void {
    this.matchService.getMatchesWithFilters({
      page: 0,
      size: 8,
      sortBy: 'startTime',
      direction: 'asc'
    }).subscribe({
      next: (response) => {
        this.upcomingMatches = response.content;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  logoUrl(path: string | undefined): string {
    return path ? '/api/images/' + path : '';
  }
}

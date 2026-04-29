import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchService } from '../../core/services/match.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-list-match',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './list-match.component.html',
  styleUrl: './list-match.component.scss'
})
export class ListMatchComponent implements OnInit {
  matches: any[] = [];
  totalItems = 0;
  currentPage = 1;
  itemsPerPage = 10;
  selectedSport: string = 'all';
  leagues: any[] = [];
  selectedLeague: any | null = null;

  constructor(private matchService: MatchService, private router: Router) { }

  ngOnInit(): void {
    this.loadMatches();
  }

  filterBySport(sport: string): void {
    this.selectedSport = sport;
    this.currentPage = 1;
    this.loadLeagues(sport);
  }
  goToMatchDetail(publicId: string): void {
    this.router.navigate(['/match', publicId]);
  }

  onLeagueChange(): void {
    this.currentPage = 1;
    this.loadMatches();
  }

  loadMatches(): void {
    const sportFilter = this.selectedSport !== 'all' ? this.selectedSport : undefined;
    const leagueId = this.selectedLeague?.id;

    const params = {
      page: this.currentPage - 1,
      size: this.itemsPerPage,
      sortBy: 'startTime',
      direction: 'asc',
      sport: sportFilter,
      leagueId: leagueId
    };

    this.matchService.getMatchesWithFilters(params).subscribe({
      next: (response) => {
        this.matches = response.content;
        this.totalItems = response.totalElements;
      },
      error: (err) => console.error('Erreur lors du chargement des matchs', err)
    });
  }




  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  pages(): number[] {
    const visiblePages = 5;
    const pages = [];

    let startPage = Math.max(1, this.currentPage - Math.floor(visiblePages / 2));
    let endPage = startPage + visiblePages - 1;

    if (endPage > this.totalPages) {
      endPage = this.totalPages;
      startPage = Math.max(1, endPage - visiblePages + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    return pages;
  }


  changePage(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.loadMatches();
  }

  loadLeagues(sport: string): void {
    if (sport === 'all') {
      this.leagues = [];
      this.selectedLeague = null;
      this.loadMatches();
      return;
    }

    this.matchService.getLeaguesBySport(sport).subscribe({
      next: (leagues) => {
        this.leagues = leagues;
        this.selectedLeague = null;
        this.loadMatches();
      },
      error: (err) => console.error('Erreur lors du chargement des ligues', err)
    });
  }

  getBestOdds(match: any): any {
    const best = {
      homeWin: { value: 0, bookmaker: '' },
      draw: { value: 0, bookmaker: '' },
      awayWin: { value: 0, bookmaker: '' },
    };

    for (const odd of match.odds || []) {
      if (odd.homeWin > best.homeWin.value) {
        best.homeWin.value = odd.homeWin;
        best.homeWin.bookmaker = odd.bookmaker;
      }
      if (odd.draw != null && odd.draw > best.draw.value) {
        best.draw.value = odd.draw;
        best.draw.bookmaker = odd.bookmaker;
      }
      if (odd.awayWin > best.awayWin.value) {
        best.awayWin.value = odd.awayWin;
        best.awayWin.bookmaker = odd.bookmaker;
      }
    }

    return best;
  }

}

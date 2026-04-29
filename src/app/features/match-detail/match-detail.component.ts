import { Standing } from '../../core/models/standing.model';
import { MarkdownModule } from 'ngx-markdown';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatchService } from '../../core/services/match.service';
import { Match } from '../../core/models/match.model';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';
import { StandingService } from '../../core/services/standing.service';

@Component({
  selector: 'app-match-detail',
  standalone: true,
  imports: [CommonModule, MarkdownModule],
  templateUrl: './match-detail.component.html',
  styleUrl: './match-detail.component.scss'
})
export class MatchDetailComponent implements OnInit {
  match?: Match;
  standings: Standing[] = [];

  selectedTab: 'analyse' | 'classement' = 'analyse';
  constructor(
    private route: ActivatedRoute,
    private matchService: MatchService,
    private location: Location,
    public standingService: StandingService
  ) { }

  ngOnInit(): void {
    const publicId = this.route.snapshot.paramMap.get('publicId');

    if (publicId) {
      this.matchService.getMatchById(publicId).subscribe({
        next: (res) => {
          this.match = res
          console.log('???', res);
        },
        error: (err) => console.error('Erreur chargement match', err)
      });
    }
  }

  selectTab(tab: 'analyse' | 'classement'): void {
    console.log("selectTab?", tab);

    this.selectedTab = tab;
    if (tab === 'classement') {
      this.loadStandingIfNeeded();
    }
  }

  loadStandingIfNeeded(): void {
    console.log("this.selectedTab", this.selectedTab);

    if (this.selectedTab === 'classement' && this.match?.league.id && this.standings.length === 0) {
      this.standingService.getStandingByLeague(this.match.league.id).subscribe({
        next: (res) => {
          this.standings = res
          console.log("res", res)
        },
        error: (err) => console.error('Erreur chargement classement', err)
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  public sanitizeMarkdown(input: string): string {
    return input
      .replace(/^\s*[-]{3,}\s*$/gm, '')            // supprime les lignes de tirets seules
      .replace(/^```/gm, '')                       // supprime les blocs de code ``` si présents
      .replace(/^[ \t]{4,}/gm, '')                 // supprime toute indentation de 4+ espaces en début de ligne
      .replace(/(\*\*.+?\*\*)(?!\n\n)/g, '$1\n\n') // force un saut de ligne après les titres
      .replace(/[ \t]+$/gm, '')
      .trimStart();                                // nettoyage de l’espace initial
  }
}

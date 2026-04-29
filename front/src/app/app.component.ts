import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  template: `<div id="main">

    <header class="navbar is-flex is-align-items-center is-justify-content-space-between px-4 py-2" [ngClass]="theme">
  <div class="navbar-brand">
    <strong class="navbar-item">YouProno</strong>
  </div>

  <div class="navbar-end">
    <button class="button is-light" (click)="toggleTheme()" aria-label="Changer de thème">
      <span class="icon">
        <i [ngClass]="theme === 'is-dark' ? 'fas fa-moon' : 'fas fa-sun'" [ngStyle]="{
            color: theme === 'is-dark' ? 'hsla(256deg, 89%, 55.29%, 1)'
            : 'hsla(53deg, 93%, 54.31%, 1)'
          }"></i>
      </span>
    </button>
  </div>
</header>


    <main class="section" id="outlet-container">
      <router-outlet></router-outlet>
    </main>

    <footer class="footer has-text-centered" [ngClass]="theme">
      <p>&copy; {{ year }}YouProno powered by Wammy</p>
    </footer></div>
  `
})
export class AppComponent {
  year = new Date().getFullYear();
  theme: 'is-light' | 'is-dark' = 'is-light';

  toggleTheme(): void {
    this.theme = this.theme === 'is-light' ? 'is-dark' : 'is-light';
    document.body.classList.toggle('dark-theme', this.theme === 'is-dark');
  }
}

import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { MarkdownModule } from 'ngx-markdown';
import { SecurityContext } from '@angular/core';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    ...MarkdownModule.forRoot({
      sanitize: SecurityContext.NONE, // 👈 désactive le nettoyage pour garder les balises HTML générées par le parser Markdown
    }).providers!

  ]
}).catch(err => console.error(err));
